package com.der.jwt_token.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;

final class AccessTokenService extends JwtTokenService {
    @Value("${jwtCookieName}")
    private String jwtCookie;
    
    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    private String accessPathCookie = "/api/auth/";

    public ResponseCookie generateAccessCookie(Long id){
        String jwtToken = generateJwtTokenFromId(id, jwtExpirationMs);
        return generateCookieValueByName(jwtCookie, jwtToken, accessPathCookie);
    }
}