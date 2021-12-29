package com.dio.tqi.apibanco.repository;

import com.dio.tqi.apibanco.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
