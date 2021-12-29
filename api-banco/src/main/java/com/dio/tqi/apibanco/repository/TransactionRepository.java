package com.dio.tqi.apibanco.repository;

import com.dio.tqi.apibanco.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
