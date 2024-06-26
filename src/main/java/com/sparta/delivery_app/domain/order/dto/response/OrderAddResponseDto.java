package com.sparta.delivery_app.domain.order.dto.response;

import com.sparta.delivery_app.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OrderAddResponseDto {
    private String storeName;
    private List<MenuItemResponseDto> menuList;
    private Long totalPrice;
    private String orderStatus;

    public static OrderAddResponseDto of(Order order, Long totalPrice) {
        return OrderAddResponseDto.builder()
                .storeName(order.getStore().getStoreName())
                .menuList(order.getOrderItemList().stream().map(MenuItemResponseDto::of).toList())
                .totalPrice(totalPrice)
                .orderStatus(order.getOrderStatus().getOrderStatusName())
                .build();
    }
}
