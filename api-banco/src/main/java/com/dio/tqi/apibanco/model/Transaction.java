package com.dio.tqi.apibanco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document (collation = "transaction")
public class Transaction {
    @Id
    private String id;
    private String keyPixSender;
    private Float value;
    private String keyPixReceiver;
    private boolean status;
    private boolean processed;
}
