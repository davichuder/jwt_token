package com.der.jwt_token.payload.response;

import java.util.List;
import java.util.stream.Collectors;

import com.der.jwt_token.security.auth.UserDetailsImpl;

public record LoginResponse(String username,
        String email,
        List<String> roles) {

    public LoginResponse(UserDetailsImpl userDetails) {
        this(userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList()));
    }
}
