package com.projeto.clube_assinatura.model;

import lombok.Getter;

@Getter
public enum UserRole {

    USER("user"),
    ADMIN("admin");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

}
