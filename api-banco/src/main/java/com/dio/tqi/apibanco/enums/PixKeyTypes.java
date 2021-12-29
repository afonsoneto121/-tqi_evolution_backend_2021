package com.dio.tqi.apibanco.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PixKeyTypes {
    PHONE("celular"),
    EMAIL("email"),
    CPF("cpf"),
    RANDOM("aleatório");

    private final String description;
}
