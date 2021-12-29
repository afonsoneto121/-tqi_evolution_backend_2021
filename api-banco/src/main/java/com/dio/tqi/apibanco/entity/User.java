package com.dio.tqi.apibanco.entity;

import com.dio.tqi.apibanco.enums.KeyPixTypes;
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
    private List<KeyPixTypes> keyPix;

}

