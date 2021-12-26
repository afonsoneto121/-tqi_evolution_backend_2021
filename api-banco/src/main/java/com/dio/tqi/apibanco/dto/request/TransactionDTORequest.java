package com.dio.tqi.apibanco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class TransactionDTORequest{

    @NotBlank
    private String keyPixSender;

    @Min(value = 0)
    private Float value;

    @NotBlank
    private String keyPixReceiver;

}
