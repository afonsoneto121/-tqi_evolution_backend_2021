package com.dio.tqi.apibanco.service;

import com.dio.tqi.apibanco.dto.Message;
import com.dio.tqi.apibanco.dto.request.PixKeyDTORequest;
import com.dio.tqi.apibanco.dto.response.StatusKeyDTOResponse;
import com.dio.tqi.apibanco.entity.PixKey;
import com.dio.tqi.apibanco.exception.KeyAlreadyExists;
import com.dio.tqi.apibanco.exception.NotAuthorizedException;
import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.exception.UserAlreadyExist;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.repository.UserRepository;
import com.dio.tqi.apibanco.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final SecurityUtil securityUtil;

    @Value("${api.bank.name}")
    private String bank;

    public User save(User user) throws UserAlreadyExist {
        Optional<User> userAlreadyExist = findUserAlreadyExist(user.getEmail());
        if (userAlreadyExist.isPresent()) {
            throw new UserAlreadyExist("User already exist");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setBank(bank);
        return repository.save(user);
    }

    public User userById(String id) throws UsernameNotFoundException {
        Optional<User> byId = repository.findById(id);
        return byId.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<User> findUserByKey(String key) {
        if (repository.findByKey(key).isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(repository.findByKey(key).get(0));
        }
    }

    private Optional<User> findUserAlreadyExist(String email) {
        List<User> byEmail = repository.findByEmail(email);
        return byEmail.isEmpty() ?
                Optional.empty() :
                Optional.of(byEmail.get(0));
    }

    public Message addPixKey(String idUser, PixKeyDTORequest dtoRequest, HttpServletRequest request) throws KeyAlreadyExists, NotFound, NotAuthorizedException {
        String strResponse = "OK";
        securityUtil.authorizedUser(request, idUser);
        ArrayList<PixKey> keys = extractParamsSelected(dtoRequest);
        Set<PixKey> validKeys = extractValidKeys(keys);

        if (validKeys.isEmpty()) {
            throw new KeyAlreadyExists("Key already exist");
        }

        // Verify if pix key is unique in other bank
        if (keys.size() > validKeys.size()) {
            strResponse = "Some key don\'t could to be saved";
        }

        Optional<User> byId = repository.findById(idUser);
        User user = byId.orElseThrow(() -> new NotFound());
        user.getPixKey().addAll(validKeys);
        repository.save(user);

        List<String> collect = keys.stream().map(value -> value.getKey()).collect(Collectors.toList());

        Message messageResponse = Message.builder()
                .message(strResponse)
                .other("Verify status in GET /api/v1/users/{id}/keys/" + String.join(",", collect))
                .build();
        return messageResponse;
    }



    private ArrayList<PixKey> extractParamsSelected(PixKeyDTORequest dtoRequest) {
        if (dtoRequest.getKeys() != null) {
            return dtoRequest.getKeys();
        }
        ArrayList<PixKey> list = new ArrayList<>();
        list.add(dtoRequest.getKey());
        return list;
    }

    private Set<PixKey> extractValidKeys(List<PixKey> keys) {
        return keys.stream().filter(key -> {
            boolean exist = pixKeyAlreadyExist(key);
            if (exist) {
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toSet());

    }

    private boolean pixKeyAlreadyExist(PixKey pixKey) {

        List<User> byKey = repository.findByKey(pixKey.getKey());
        return !byKey.isEmpty();
    }





    public List<StatusKeyDTOResponse> statusKeys(String id, List<String> keys, HttpServletRequest request) throws NotAuthorizedException {
        securityUtil.authorizedUser(request, id);
        Optional<User> optionalUser = repository.findById(id);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> userKeys = user.getPixKey().stream().map(value -> value.getKey()).collect(Collectors.toList());
        return keys.stream().map((String key) -> {
            StatusKeyDTOResponse status = new StatusKeyDTOResponse(key);
            if (userKeys.contains(key)) {
                status.setStatus(true);
            } else {
                status.setStatus(false);
            }

            return status;
        }).collect(Collectors.toList());
    }

    public boolean userHasBalance(float value, String key) {
        List<User> byKey = repository.findByKey(key);
        User user = byKey.get(0);
        if (user.getBalance() >= value) {
            user.setBalance(user.getBalance() - value);
            repository.save(user);
            return true;
        }
        return false;
    }

    public void transferValue(float value, String pixKeyReceiver) {
        List<User> byKey = repository.findByKey(pixKeyReceiver);
        User user = byKey.get(0);
        if (user.getBalance() >= value) {
            user.setBalance(user.getBalance() + value);
            repository.save(user);
        }
    }
}
