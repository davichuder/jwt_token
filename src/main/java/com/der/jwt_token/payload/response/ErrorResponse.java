package com.der.jwt_token.payload.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int statusCode,
        Date timestamp,
        String message,
        String description) {

    public ErrorResponse(HttpStatus status,
            String message,
            String description) {
        this(status.value(),
                new Date(),
                message,
                description);
    }
}
