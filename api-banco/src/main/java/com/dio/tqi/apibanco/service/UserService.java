package com.dio.tqi.apibanco.service;

import com.dio.tqi.apibanco.dto.Message;
import com.dio.tqi.apibanco.dto.request.PixKeyDTORequest;
import com.dio.tqi.apibanco.entity.PixKey;
import com.dio.tqi.apibanco.exception.KeyAlreadyExists;
import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.exception.UserAlreadyExist;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
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
    private final TokenService tokenService;
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

    private Optional<User> findUserAlreadyExist(String email) {
        Example<User> example = Example.of(User.builder().email(email).build());
        return repository.findOne(example);
    }

    public Message addPixKey(String idUser, PixKeyDTORequest dtoRequest, HttpServletRequest request) throws KeyAlreadyExists, NotFound {
        String token = getTokenFromHeader(request);
        String strResponse = "OK";
        if (!verifyIfUserIsAuthorized(token, idUser)) {
            throw new NotAuthorizedException("User not authorized");
        }
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
                .other("Verify status in GET /api/v1/users/keys/"+String.join(",",collect))
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

    private boolean verifyIfUserIsAuthorized(String token, String idUser) {
        return tokenService.getTokenSubject(token).equals(idUser);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
