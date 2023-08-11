package com.der.jwt_token.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
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

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getSubjectFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}