package com.dio.tqi.transfer.service;

import com.dio.tqi.transfer.model.CacheUser;
import com.dio.tqi.transfer.repository.CacheUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CacheUserService {
    private final CacheUserRepository repository;

    public Optional<CacheUser> findUser(String key) {
        return repository.findByPixKey(key);
    }

    public void save(CacheUser user) {
        repository.save(user);
    }
}
