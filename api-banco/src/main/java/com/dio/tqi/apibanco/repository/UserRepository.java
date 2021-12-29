package com.dio.tqi.apibanco.repository;

import com.dio.tqi.apibanco.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
}
