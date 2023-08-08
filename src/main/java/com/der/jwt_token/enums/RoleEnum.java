package com.der.jwt_token.enums;

import java.util.HashMap;
import java.util.Map;

public enum RoleEnum {
    ROLE_ADMIN("admin"),
    ROLE_USER("user"),
    ROLE_MODERATOR("mod");

    private final String name;

    private static final Map<String, RoleEnum> roleMap = new HashMap<>();
    static {
        for (RoleEnum role : RoleEnum.values()) {
            roleMap.put(role.name, role);
        }
    }

    private RoleEnum(String name){
        this.name = name;
    }

    public static RoleEnum validate(String role) {
        return roleMap.get(role);
    }
}
