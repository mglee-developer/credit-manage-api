package com.example.credit_manage_api.domain.user.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final String accessToken;
    private final String email;
    private final String name;

    public LoginResponse(String accessToken, String email, String name) {
        this.accessToken = accessToken;
        this.email = email;
        this.name = name;
    }
}
