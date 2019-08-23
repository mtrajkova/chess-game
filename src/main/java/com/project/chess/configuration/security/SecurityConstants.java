package com.project.chess.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static final String LOGIN_URL = "/login";
    public static final String REGISTER_URL = "/register";

    public static String SECRET;
    public static String TOKEN_PREFIX;
    public static String HEADER_STRING;
    public static long EXPIRATION_TIME;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

    @Value("${jwt.tokenPrefix}")
    public void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${jwt.header}")
    public void setHeaderString(String headerString) {
        HEADER_STRING = headerString;
    }

    @Value("${jwt.expirationTime}")
    public void setExpirationTime(long expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }
}
