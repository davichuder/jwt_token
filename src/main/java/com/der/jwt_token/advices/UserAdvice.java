package com.der.jwt_token.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.der.jwt_token.exceptions.UsernameUsedException;
import com.der.jwt_token.payload.response.ErrorResponse;

@RestControllerAdvice
public class UserAdvice {
    @ExceptionHandler(value = UsernameUsedException.class)
    public ResponseEntity<ErrorResponse> handleUsernameTakenException(UsernameUsedException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                request.getDescription(false));
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
