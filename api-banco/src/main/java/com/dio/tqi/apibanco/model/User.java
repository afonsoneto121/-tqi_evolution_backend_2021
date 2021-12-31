package com.dio.tqi.apibanco.model;

import com.dio.tqi.apibanco.entity.PixKey;
import com.dio.tqi.apibanco.enums.PixKeyTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User{
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String bank;
    private List<PixKey> pixKey;

}

