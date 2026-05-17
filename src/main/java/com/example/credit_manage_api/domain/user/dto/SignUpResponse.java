package com.example.credit_manage_api.domain.user.dto;

import com.example.credit_manage_api.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SignUpResponse {
    private final Long id;
    private final String email;
    private final String name;

    public SignUpResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
