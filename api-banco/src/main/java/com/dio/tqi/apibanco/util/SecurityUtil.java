package com.dio.tqi.apibanco.util;

import com.dio.tqi.apibanco.exception.NotAuthorizedException;
import com.dio.tqi.apibanco.model.Transaction;
import com.dio.tqi.apibanco.model.User;
import com.dio.tqi.apibanco.repository.UserRepository;
import com.dio.tqi.apibanco.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final TokenService tokenService;
    private final UserRepository repository;

    public void authorizedUser(HttpServletRequest request, String idUser) throws NotAuthorizedException {
        String token = getTokenFromHeader(request);
        if (!verifyIfUserIsAuthorized(token, idUser)) {
            throw new NotAuthorizedException("User not authorized");
        }
    }

    public void authorizedUser(HttpServletRequest request, Transaction transaction) throws NotAuthorizedException {
        String key = transaction.getKeyPixSender();
        String token = getTokenFromHeader(request);
        List<User> usersByKey = repository.findByKey(key);
        if(usersByKey.isEmpty()) {
            throw new NotAuthorizedException("User not authorized");
        } else {
            User user = usersByKey.get(0);
            if (!verifyIfUserIsAuthorized(token, user.getId())) {
                throw new NotAuthorizedException("User not authorized");
            }
        }

    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }

    private boolean verifyIfUserIsAuthorized(String token, String idUser) {
        return tokenService.getTokenSubject(token).equals(idUser);
    }

}
