package com.der.jwt_token.exceptions;

public class UsernameUsedException extends Exception {
    public UsernameUsedException() {
        super("Error: Username is already used!");
    }
}