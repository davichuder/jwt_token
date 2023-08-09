package com.der.jwt_token.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.der.jwt_token.enums.RoleEnum;
import com.der.jwt_token.exceptions.EmailUsedException;
import com.der.jwt_token.exceptions.RoleNotFoundException;
import com.der.jwt_token.exceptions.RolesEmptyException;
import com.der.jwt_token.exceptions.UsernameUsedException;
import com.der.jwt_token.models.Role;
import com.der.jwt_token.models.User;
import com.der.jwt_token.payload.request.SignUpRequest;
import com.der.jwt_token.repositories.RoleRepository;
import com.der.jwt_token.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void newUser(SignUpRequest signUpRequest)
            throws UsernameUsedException, EmailUsedException, RolesEmptyException, RoleNotFoundException {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            throw new UsernameUsedException();
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new EmailUsedException();
        }

        if (signUpRequest.roles() == null) {
            throw new RolesEmptyException();
        }

        Set<Role> roles = new HashSet<>();

        for (String item : signUpRequest.roles()) {
            RoleEnum roleEnum = RoleEnum.validate(item);
            if (roleEnum == null) {
                throw new RoleNotFoundException(item);
            }
            Role role = roleRepository.findByName(roleEnum).get();
            roles.add(role);
        }

        User user = new User(signUpRequest.username(),
                         signUpRequest.email(),
                         new BCryptPasswordEncoder().encode(signUpRequest.password()),
                         roles);
        
        userRepository.save(user);
    }
}
