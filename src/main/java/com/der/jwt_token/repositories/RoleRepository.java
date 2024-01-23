package com.der.jwt_token.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.der.jwt_token.enums.RoleEnum;
import com.der.jwt_token.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(RoleEnum name);
}