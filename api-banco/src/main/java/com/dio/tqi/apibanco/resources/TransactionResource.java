package com.dio.tqi.apibanco.resources;

import com.dio.tqi.apibanco.dto.request.TransactionDTORequest;
import com.dio.tqi.apibanco.dto.response.TransactionDTOResponse;
import com.dio.tqi.apibanco.model.Transaction;
import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.mapper.TransactionMapper;
import com.dio.tqi.apibanco.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionResource {
    private final TransactionMapper transactionMapper;
    private final TransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTOResponse saveTransaction(@RequestBody @Valid TransactionDTORequest dtoRequest) {
        log.info("Saving new transaction ", dtoRequest);
        Transaction transactionToSave = transactionMapper.DTORequestToModel(dtoRequest);
        return transactionMapper.ModelToDTOResponse(service.save(transactionToSave));
    }
    @GetMapping
    public TransactionDTOResponse findById(@RequestParam String id) throws NotFound {
        Transaction transaction = service.findById(id);
        return transactionMapper.ModelToDTOResponse(transaction);
    }
}
