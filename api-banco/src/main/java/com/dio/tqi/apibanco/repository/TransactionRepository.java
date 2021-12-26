package com.dio.tqi.apibanco.repository;

import com.dio.tqi.apibanco.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
