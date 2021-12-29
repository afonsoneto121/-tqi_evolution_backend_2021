package com.dio.tqi.apibanco.mapper;

import com.dio.tqi.apibanco.dto.request.TransactionDTORequest;
import com.dio.tqi.apibanco.dto.response.TransactionDTOResponse;
import com.dio.tqi.apibanco.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    private final ObjectMapper objectMapper;

    public Transaction DTORequestToModel(TransactionDTORequest transactionDTORequest) {
        return objectMapper.convertValue(transactionDTORequest,Transaction.class);
    }
    public TransactionDTORequest ModelToDTORequest(Transaction transaction) {
        return objectMapper.convertValue(transaction,TransactionDTORequest.class);
    }
    public TransactionDTOResponse ModelToDTOResponse(Transaction transaction) {
        return TransactionDTOResponse.builder()
                .id(transaction.getId())
                .status(transaction.isStatus())
                .processed(transaction.isProcessed())
                .build();
    }
}
