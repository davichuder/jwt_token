package com.der.jwt_token.payload.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username,
        @NotBlank String password) {
}
