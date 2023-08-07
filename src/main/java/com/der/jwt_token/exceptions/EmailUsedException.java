package com.der.jwt_token.exceptions;

public class EmailUsedException extends Exception {
    public EmailUsedException() {
        super("Error: Email is already taken!");
    }
}