package com.dio.tqi.apibanco.service;

import com.dio.tqi.apibanco.data.LoginData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {
    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;



    public String generateToken(LoginData loginData) {
        Key KEY = Keys.hmacShaKeyFor(secret.getBytes());
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API Banco")
                .setSubject(loginData.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(KEY).compact();
    }
    public boolean isTokenValid(String token) {
        Key KEY = Keys.hmacShaKeyFor(secret.getBytes());
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenSubject(String token) {
        Key KEY = Keys.hmacShaKeyFor(secret.getBytes());
        Claims body = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }
}
