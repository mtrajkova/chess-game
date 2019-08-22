package com.project.chess.configuration.security;

public class SecurityConstants {
    public static final String LOGIN_URL = "/user/login";
    public static final String REGISTER_URL = "/user/registration";

    //  https://www.allkeysgenerator.com/ is used to generate the secret key. Options - Encryption key - 512-bit
    public static final String SECRET = "C*F-JaNdRgUjXn2r5u8x/A?D(G+KbPeShVmYp3s6v9y$B&E)H@McQfTjWnZr4t7w";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 864_000_000;
}
