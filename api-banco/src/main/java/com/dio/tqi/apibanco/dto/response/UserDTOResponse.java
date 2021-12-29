package com.dio.tqi.apibanco.dto.response;

import com.dio.tqi.apibanco.enums.PixKeyTypes;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTOResponse {
    private String id;
    private String name;
    private String email;
    private String bank;
    private List<PixKeyTypes> keyPix;
}
