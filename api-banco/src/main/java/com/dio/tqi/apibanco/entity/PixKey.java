package com.dio.tqi.apibanco.entity;

import com.dio.tqi.apibanco.enums.PixKeyTypes;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PixKey {
    @NotNull
    private PixKeyTypes pixTypes;
    @NotBlank
    private String key;
}
