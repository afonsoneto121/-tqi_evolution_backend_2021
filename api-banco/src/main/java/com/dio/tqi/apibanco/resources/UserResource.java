package com.dio.tqi.apibanco.resources;

import com.dio.tqi.apibanco.dto.Message;
import com.dio.tqi.apibanco.dto.request.PixKeyDTORequest;
import com.dio.tqi.apibanco.dto.request.UserDTORequest;
import com.dio.tqi.apibanco.dto.response.UserDTOResponse;
import com.dio.tqi.apibanco.exception.KeyAlreadyExists;
import com.dio.tqi.apibanco.exception.NotFound;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.exception.UserAlreadyExist;
import com.dio.tqi.apibanco.mapper.UserMapper;
import com.dio.tqi.apibanco.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserMapper mapper;
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserDTOResponse> createUser(@RequestBody @Valid UserDTORequest dtoRequest) throws UserAlreadyExist {
        User user = mapper.dtoRequestToUser(dtoRequest);
        User save = service.save(user);
        UserDTOResponse response = mapper.userToDTOResponse(save);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Message> addPixKey(@RequestBody @Valid PixKeyDTORequest dtoRequest,
                                             @PathVariable String id,
                                             HttpServletRequest request) throws KeyAlreadyExists, NotFound {
        Message message = service.addPixKey(id, dtoRequest, request);
        return ResponseEntity.ok(message);
    }


}
