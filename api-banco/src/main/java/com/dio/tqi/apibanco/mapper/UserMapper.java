package com.dio.tqi.apibanco.mapper;

import com.dio.tqi.apibanco.dto.request.UserDTORequest;
import com.dio.tqi.apibanco.dto.response.UserDTOResponse;
import com.dio.tqi.apibanco.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ObjectMapper objectMapper;

    public User dtoRequestToUser(UserDTORequest dtoRequest) {
        return objectMapper.convertValue(dtoRequest, User.class);
    }
    public UserDTOResponse userToDTOResponse(User user) {
        return objectMapper.convertValue(user, UserDTOResponse.class);
    }

}
