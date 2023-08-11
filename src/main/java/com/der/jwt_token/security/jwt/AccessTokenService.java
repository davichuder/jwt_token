package com.der.jwt_token.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

public class AccessTokenService extends JwtTokenService {
    @Value("${jwtCookieName}")
    private String jwtCookieName;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public ResponseCookie generateAccessJwtCookie(Long id) {
        String jwtToken = generateJwtTokenFromSubject(Long.toString(id), jwtExpirationMs);
        return generateCookieValueByName(jwtCookieName, jwtToken, accessPathCookie);
    }

    public ResponseCookie generateAccessJwtCookie(String id) {
        String jwtToken = generateJwtTokenFromSubject(id, jwtExpirationMs);
        return generateCookieValueByName(jwtCookieName, jwtToken, accessPathCookie);
    }

    public ResponseCookie getCleanJwtAccessCookie() {
        return cleanCookie(jwtCookieName, accessPathCookie);
    }
}