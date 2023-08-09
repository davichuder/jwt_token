package com.der.jwt_token.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenService {
    protected static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    protected final String accessPathCookie = "/api/auth/";

    protected Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    protected String generateJwtTokenFromSubject(String subject, int msDuration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + msDuration))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    protected ResponseCookie generateCookieValueByName(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path(path)
                .maxAge(7 * 24 * 60 * 60)
                .httpOnly(true)
                .build();
        return cookie;
    }

    protected String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    protected ResponseCookie cleanCookie(String nameCookie, String path) {
        ResponseCookie cookie = ResponseCookie.from(nameCookie, null)
                .path(path)
                .build();
        return cookie;
    }
}