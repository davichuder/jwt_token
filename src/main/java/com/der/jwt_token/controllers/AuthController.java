package com.der.jwt_token.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.der.jwt_token.exceptions.EmailUsedException;
import com.der.jwt_token.exceptions.RoleNotFoundException;
import com.der.jwt_token.exceptions.RolesEmptyException;
import com.der.jwt_token.exceptions.UsernameUsedException;
import com.der.jwt_token.payload.request.LoginRequest;
import com.der.jwt_token.payload.request.SignUpRequest;
import com.der.jwt_token.payload.response.LoginResponse;
import com.der.jwt_token.payload.response.MessageResponse;
import com.der.jwt_token.security.auth.UserDetailsImpl;
import com.der.jwt_token.security.jwt.AccessTokenService;
import com.der.jwt_token.security.jwt.RefreshTokenService;
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
    AccessTokenService accessTokenService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
            throws UsernameUsedException, EmailUsedException, RolesEmptyException, RoleNotFoundException {
        userService.newUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
                        loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = accessTokenService.generateAccessJwtCookie(userDetails.getId());

        ResponseCookie jwtRefreshCookie = refreshTokenService.generateRefreshJwtCookie(userDetails.getId());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new LoginResponse(userDetails));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = refreshTokenService.getJwtRefreshFromCookies(request);

        if (refreshToken != null && refreshTokenService.validateJwtToken(refreshToken)) {
            String id = refreshTokenService.getSubjectFromJwtToken(refreshToken);
            ResponseCookie jwtCookie = accessTokenService.generateAccessJwtCookie(id);
            ResponseCookie jwtRefreshCookie = refreshTokenService.generateRefreshJwtCookie(id);

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                    .body(new MessageResponse("Token is refreshed successfully!"));
        }

        return ResponseEntity.badRequest().body(new MessageResponse("Refresh Token is not valid!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie jwtAccessCookie = accessTokenService.getCleanJwtAccessCookie();
        ResponseCookie jwtRefreshCookie = refreshTokenService.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
