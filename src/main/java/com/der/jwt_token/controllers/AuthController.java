package com.der.jwt_token.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.der.jwt_token.exceptions.EmailUsedException;
import com.der.jwt_token.exceptions.UsernameUsedException;
import com.der.jwt_token.payload.request.LoginRequest;
import com.der.jwt_token.payload.request.SignUpRequest;
import com.der.jwt_token.payload.response.MessageResponse;
import com.der.jwt_token.security.jwt.RefreshTokenService;
import com.der.jwt_token.security.jwt.TokenService;
import com.der.jwt_token.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    TokenService jwtService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
            throws UsernameUsedException, EmailUsedException {
        userService.newUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return null;
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        return null;
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return null;
    }
}
