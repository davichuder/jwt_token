package com.der.jwt_token.exceptions;

public class RolesEmptyException extends Exception {
    public RolesEmptyException() {
        super("Error: Roles is empty!");
    }
}
