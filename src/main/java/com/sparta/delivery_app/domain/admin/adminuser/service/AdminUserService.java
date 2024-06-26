package com.sparta.delivery_app.domain.admin.adminuser.service;

import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.admin.adminuser.dto.AdminUserResponseDto;
import com.sparta.delivery_app.domain.admin.adminuser.dto.PageAdminUserResponseDto;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.user.adapter.UserAdapter;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.delivery_app.domain.commen.page.util.PageUtil.*;
import static com.sparta.delivery_app.domain.user.entity.UserStatus.checkManagerEnable;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminUserService {

    private final UserAdapter userAdapter;

    public PageAdminUserResponseDto getAllUserList(
            AuthenticationUser authenticationUser, Boolean isDesc, Integer pageNum)
    {
        //ADMIN 권한의 유저 Status 가 ENABLE 인지 확인
        String email = authenticationUser.getUsername();
        User enableUser = userAdapter.queryUserByEmail(email);
        checkManagerEnable(enableUser);

        //페이징
        Pageable pageable = createPageable(pageNum, PAGE_SIZE_FIVE, isDesc);
        Page<User> allUser = userAdapter.queryAllUserPage(pageable);
        String totalUser = validateAndSummarizePage(pageNum,allUser);

        //user 정보 추출
        Page<AdminUserResponseDto> responseDtoPage = allUser.map(AdminUserResponseDto::of);

        return PageAdminUserResponseDto.of(pageNum, responseDtoPage, totalUser);
    }
}
