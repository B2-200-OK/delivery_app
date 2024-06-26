package com.sparta.delivery_app.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    CONSUMER("CONSUMER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String userRoleName;
}
