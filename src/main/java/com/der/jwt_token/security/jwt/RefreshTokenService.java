package com.der.jwt_token.security.jwt;

import org.springframework.beans.factory.annotation.Value;

public class RefreshTokenService extends JwtTokenService {
    @Value("${jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Value("${jwtRefreshCookieName}")
    private String jwtRefreshCookie;
}