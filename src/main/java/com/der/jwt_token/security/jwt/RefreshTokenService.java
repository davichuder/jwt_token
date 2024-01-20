package com.der.jwt_token.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class RefreshTokenService extends JwtTokenService {
    @Value("${jwtRefreshCookieName}")
    private String jwtRefreshCookieName;

    @Value("${jwtRefreshExpirationMs}")
    private int refreshTokenDurationMs;

    protected final String refreshPathCookie = "/api/auth/refreshtoken";

    public ResponseCookie generateRefreshTokenCookie(Long id) {
        String jwtToken = generateJwtTokenFromSubject(Long.toString(id), refreshTokenDurationMs);
        return generateCookieValueByName(jwtRefreshCookieName, jwtToken, refreshPathCookie);
    }

    public ResponseCookie generateRefreshTokenCookie(String id) {
        String jwtToken = generateJwtTokenFromSubject(id, refreshTokenDurationMs);
        return generateCookieValueByName(jwtRefreshCookieName, jwtToken, refreshPathCookie);
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookieName);
    }

    public ResponseCookie cleanRefreshTokenCookie() {
        return cleanCookie(jwtRefreshCookieName, refreshPathCookie);
    }
}