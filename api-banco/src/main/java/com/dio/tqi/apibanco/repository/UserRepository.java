package com.dio.tqi.apibanco.repository;

import com.dio.tqi.apibanco.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    @Query(" {'pixKey.key' : ?0 }")
    List<User> findByKey(String keyName);
    @Query(" {'email' : ?0 }")
    List<User> findByEmail(String email);
}
