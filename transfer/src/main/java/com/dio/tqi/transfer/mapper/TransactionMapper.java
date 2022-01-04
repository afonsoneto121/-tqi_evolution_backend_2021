package com.dio.tqi.transfer.mapper;

import com.dio.tqi.transfer.model.Transaction;
import com.example.transfer.schema.TransactionAvro;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    public Transaction avroToModel(TransactionAvro avro) {
        return Transaction.builder()
                .keyPixSender(avro.getPixKeySender())
                .bankNameSender(avro.getBankNameSender())
                .keyPixReceiver(avro.getPixKeyReceiver())
                .bankNameReceiver(avro.getBankNameReceiver())
                .value(avro.getValue())
                .status(avro.getStatus())
                .processed(avro.getProcessed())
                .build();
    }
}
