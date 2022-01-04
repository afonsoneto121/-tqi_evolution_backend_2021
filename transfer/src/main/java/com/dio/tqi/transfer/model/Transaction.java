package com.dio.tqi.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    private String id;
    private String keyPixSender;
    private Float value;
    private String keyPixReceiver;
    private boolean status;
    private boolean processed;

}