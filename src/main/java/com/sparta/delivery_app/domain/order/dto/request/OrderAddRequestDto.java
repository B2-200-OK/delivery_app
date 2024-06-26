package com.sparta.delivery_app.domain.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public record OrderAddRequestDto (
        @NotNull(message = "매장 아이디가 입력되지 않았습니다.")
        Long storeId,

        @Valid
        @NotEmpty(message = "1개 이상 주문해 주세요.")
        List<MenuItemRequestDto> menuList
){
}
