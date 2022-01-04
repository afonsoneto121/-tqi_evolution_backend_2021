package com.dio.tqi.transfer.mapper;

import com.dio.tqi.transfer.model.CacheUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheUserMapper {
    private final ObjectMapper objectMapper;

}
