package com.der.jwt_token.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletRequest;

public class RefreshTokenService extends JwtTokenService {
    @Value("${jwtRefreshCookieName}")
    private String jwtRefreshCookieName;

    @Value("${jwtRefreshExpirationMs}")
    private int refreshTokenDurationMs;

    public ResponseCookie generateRefreshJwtCookie(Long id) {
        String jwtToken = generateJwtTokenFromSubject(Long.toString(id), refreshTokenDurationMs);
        return generateCookieValueByName(jwtRefreshCookieName, jwtToken, accessPathCookie);
    }

    public ResponseCookie generateRefreshJwtCookie(String id) {
        String jwtToken = generateJwtTokenFromSubject(id, refreshTokenDurationMs);
        return generateCookieValueByName(jwtRefreshCookieName, jwtToken, accessPathCookie);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookieName);
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return cleanCookie(jwtRefreshCookieName, accessPathCookie);
    }
}