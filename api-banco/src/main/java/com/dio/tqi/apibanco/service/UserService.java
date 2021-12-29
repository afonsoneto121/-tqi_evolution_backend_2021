package com.dio.tqi.apibanco.service;

import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.exception.UserAlreadyExist;
import com.dio.tqi.apibanco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    @Value("${api.bank.name}")
    private String bank;

    public User save(User user) throws UserAlreadyExist {
        Optional<User> userAlreadyExist = findUserAlreadyExist(user.getEmail());
        if(userAlreadyExist.isPresent()) {
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
}
