package com.dio.tqi.apibanco.resources;

import com.dio.tqi.apibanco.dto.request.LoginDTORequest;
import com.dio.tqi.apibanco.dto.response.TokenDTOResponse;
import com.dio.tqi.apibanco.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationResource {


    /*@PostMapping
    public ResponseEntity<TokenDTOResponse> auth(@RequestBody @Valid LoginDTORequest login) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(TokenDTOResponse.builder()
                        .type("Bearer")
                        .token(token)
                .build());
    }*/
}
