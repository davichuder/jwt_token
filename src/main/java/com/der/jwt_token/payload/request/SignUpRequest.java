package com.der.jwt_token.payload.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(@NotBlank @Size(min = 3, max = 32) String username,
        @NotBlank @Size(max = 64) @Email String email,
        Set<String> roles,
        @NotBlank @Size(min = 8, max = 64) String password) {
}
