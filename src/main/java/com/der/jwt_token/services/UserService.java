package com.der.jwt_token.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.der.jwt_token.exceptions.EmailUsedException;
import com.der.jwt_token.exceptions.UsernameUsedException;
import com.der.jwt_token.payload.request.SignUpRequest;
import com.der.jwt_token.repositories.RoleRepository;
import com.der.jwt_token.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void newUser(SignUpRequest signUpRequest) throws UsernameUsedException, EmailUsedException{
        if (userRepository.existsByUsername(signUpRequest.username())) {
            throw new UsernameUsedException();
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new EmailUsedException();
        }
    }
}
