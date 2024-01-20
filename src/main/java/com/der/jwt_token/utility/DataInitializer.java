package com.der.jwt_token.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.der.jwt_token.services.RoleService;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) {
        roleService.loadRoles();
    }
}