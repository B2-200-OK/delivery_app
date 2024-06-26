package com.sparta.delivery_app.domain.admin.adminuser.dto;

import com.sparta.delivery_app.domain.user.entity.User;
import com.sparta.delivery_app.domain.user.entity.UserRole;
import com.sparta.delivery_app.domain.user.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdminUserResponseDto {

    private Long id;
    private String email;
    private String name;
    private String nickName;
    private String userAddress;
    private UserStatus userStatus;
    private UserRole userRole;

    public static AdminUserResponseDto of(User user) {
        return AdminUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickName(user.getNickName())
                .userAddress(user.getUserAddress())
                .userStatus(user.getUserStatus())
                .userRole(user.getUserRole())
                .build();
    }

}
