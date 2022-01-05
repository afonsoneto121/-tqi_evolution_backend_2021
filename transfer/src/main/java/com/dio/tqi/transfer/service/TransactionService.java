package com.dio.tqi.transfer.service;


import com.dio.tqi.transfer.model.Transaction;
import com.dio.tqi.transfer.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public void save(Transaction transaction) {
        repository.save(transaction);
    }
}
