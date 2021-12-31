package com.dio.tqi.apibanco.dto.response;

import com.dio.tqi.apibanco.entity.PixKey;
import lombok.Builder;
import lombok.Data;

@Data
public class StatusKeyDTOResponse {
    private String key;
    private boolean status;

    public StatusKeyDTOResponse(String pixKey) {
        this.key = pixKey;
    }
}
