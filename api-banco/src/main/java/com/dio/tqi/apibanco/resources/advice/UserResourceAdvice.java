package com.dio.tqi.apibanco.resources.advice;

import com.dio.tqi.apibanco.dto.Message;
import com.dio.tqi.apibanco.exception.KeyAlreadyExists;
import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.exception.UserAlreadyExist;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.ws.rs.NotAuthorizedException;

@RestControllerAdvice
public class UserResourceAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExist.class)
    public Message alreadyExist() {
        return Message.builder().message("Email Already exists").build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(KeyAlreadyExists.class)
    public Message keyAlreadyExists() {
        return Message.builder().message("Key Already exists").build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public Message userNotAuthorized() {
        return Message.builder().message("Resource protected").build();
    }
}
