package com.dio.tqi.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String keyPixSender;
    private String bankNameSender;
    private String keyPixReceiver;
    private String bankNameReceiver;
    private Float value;
    private boolean status;
    private boolean processed;

}