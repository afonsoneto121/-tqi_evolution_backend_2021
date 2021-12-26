package com.dio.tqi.apibanco.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionDTOResponse {
    private String id;
    private boolean status;
    private boolean processed;
}
