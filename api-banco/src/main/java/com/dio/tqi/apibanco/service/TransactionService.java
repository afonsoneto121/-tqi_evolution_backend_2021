package com.dio.tqi.apibanco.service;

import com.dio.tqi.apibanco.model.Transaction;
import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.repository.TransactionRepository;
import com.example.transfer.schema.TransactionAvro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository repository;
    private final UserService userService;
    private final StreamBridge streamBridge;

    @Value("{api.bank.name}")
    private String bank;
    public Transaction save(Transaction transaction) {
        //Add Authorization
        boolean hasBalance = userService.userHasBalance(transaction.getValue(), transaction.getKeyPixSender());
        transaction.setProcessed(false);
        transaction.setStatus(hasBalance);
        Transaction save = repository.save(transaction);
        if(hasBalance) {
            TransactionAvro avro = TransactionAvro.newBuilder()
                    .setId(save.getId())
                    .setPixKeySender(save.getKeyPixSender())
                    .setPixKeyReceiver(save.getKeyPixReceiver())
                    .setValue(save.getValue())
                    .setStatus(save.isStatus())
                    .setProcessed(save.isStatus())
                    .setBankNameReceiver("")
                    .setBankNameSender(bank)
                    .build();
            Message<TransactionAvro> message = MessageBuilder.withPayload(avro).build();
            streamBridge.send("transactionInit-out-0", message);
        }
        return save;
    }

    public Transaction findById(String id) throws NotFound {
        return repository.findById(id).orElseThrow(() -> new NotFound());
    }

    public void updateTransaction(String id, boolean status, boolean process ) throws NotFound {
        Transaction transaction = findById(id);
        transaction.setStatus(status);
        transaction.setProcessed(process);
        repository.save(transaction);
    }
}
