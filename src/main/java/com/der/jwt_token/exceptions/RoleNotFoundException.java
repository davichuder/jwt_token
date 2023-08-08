package com.der.jwt_token.exceptions;

public class RoleNotFoundException extends Exception {
    public RoleNotFoundException(String role) {
        super("Error: Role ["+ role + "] not found!");
    }
}
