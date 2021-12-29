package com.dio.tqi.apibanco.security;

import com.dio.tqi.apibanco.data.LoginData;
import com.dio.tqi.apibanco.dto.response.TokenDTOResponse;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            User loginData = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(),
                    loginData.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Login Failed ", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        LoginData login = (LoginData) authResult.getPrincipal();
        String token = tokenService.generateToken(login);
        TokenDTOResponse tokenObj = TokenDTOResponse.builder()
                .type("Bearer")
                .token(token)
                .build();

        response.addHeader("Content-Type","application/json");
        response.getWriter().write(tokenObj.toJson());
        response.getWriter().flush();
    }
}
