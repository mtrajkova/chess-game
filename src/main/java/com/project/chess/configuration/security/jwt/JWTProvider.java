package com.project.chess.configuration.security.jwt;

import com.auth0.jwt.JWT;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.project.chess.configuration.security.SecurityConstants.EXPIRATION_TIME;
import static com.project.chess.configuration.security.SecurityConstants.SECRET;

@Component
public class JWTProvider {

    public String generateToken(String username) {
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expiresAt)
                .sign(HMAC512(SECRET.getBytes()));
    }
}
