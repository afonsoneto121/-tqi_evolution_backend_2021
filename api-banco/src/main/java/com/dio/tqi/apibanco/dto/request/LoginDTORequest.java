package com.dio.tqi.apibanco.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginDTORequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
