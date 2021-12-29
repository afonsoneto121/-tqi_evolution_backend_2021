package com.dio.tqi.apibanco.dto.response;

import com.dio.tqi.apibanco.enums.KeyPixTypes;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@Builder
public class UserDTOResponse {
    private String id;
    private String name;
    private String email;
    private String bank;
    private List<KeyPixTypes> keyPix;
}
