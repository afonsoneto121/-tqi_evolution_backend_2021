package com.dio.tqi.apibanco.resources.advice;

import com.dio.tqi.apibanco.dto.Message;
import com.dio.tqi.apibanco.exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionResourceAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFound.class)
    public Message notFound() {
        return Message.builder().message("Resource not found").build();
    }
}
