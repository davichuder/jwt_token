package com.der.jwt_token.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AccessTokenService extends JwtTokenService {
    @Value("${jwtCookieName}")
    private String jwtCookieName;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public ResponseCookie generateAccessTokenCookie(Long id) {
        String jwtToken = generateJwtTokenFromSubject(Long.toString(id), jwtExpirationMs);
        return generateCookieValueByName(jwtCookieName, jwtToken, accessPathCookie);
    }

    public ResponseCookie generateAccessTokenCookie(String id) {
        String jwtToken = generateJwtTokenFromSubject(id, jwtExpirationMs);
        return generateCookieValueByName(jwtCookieName, jwtToken, accessPathCookie);
    }

    public String getAccessTokenFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookieName);
    }

    public ResponseCookie cleanAccessTokenCookie() {
        return cleanCookie(jwtCookieName, accessPathCookie);
    }
}