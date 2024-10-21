package com.example.request_solver.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, OPERATOR, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}