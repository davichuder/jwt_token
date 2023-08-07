package com.der.jwt_token.payload.response;

import jakarta.validation.constraints.NotBlank;

public record MessageResponse(@NotBlank String message) {
}
