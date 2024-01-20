package com.der.jwt_token.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.der.jwt_token.enums.RoleEnum;
import com.der.jwt_token.models.Role;
import com.der.jwt_token.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void loadRoles() {
        List<Role> newRoles = Arrays.stream(RoleEnum.values())
                .filter(roleName -> !roleRepository.findByName(roleName).isPresent())
                .map(Role::new)
                .collect(Collectors.toList());
        roleRepository.saveAll(newRoles);
    }

    public Role getRoleByEnum(RoleEnum roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }
}