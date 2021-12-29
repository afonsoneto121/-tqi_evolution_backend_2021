package com.dio.tqi.apibanco.service;

import com.dio.tqi.apibanco.data.LoginData;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = User.builder().email(username).build();
        Example<User> example = Example.of(user);

        Optional<User> optional = repository.findOne(example);
        if(optional.isEmpty()) {
            new UsernameNotFoundException("User not found");
        }
        LoginData login = new LoginData(optional);
        return login;
    }


}
