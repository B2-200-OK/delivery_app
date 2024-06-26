package com.sparta.delivery_app.domain.admin.adminstore.dto;


import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalPricePerStoreResponseDto {

    /**
     * menu hashMap 에 value 로 들어갈 responseDto
     */
    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private MenuStatus menuStatus;
    private Long menuTotalPrice;

    public static TotalPricePerStoreResponseDto of(Menu menu, Long menuTotalPrice) {
        return TotalPricePerStoreResponseDto.builder()
                .menuId(menu.getId())
                .menuName(menu.getMenuName())
                .menuPrice(menu.getMenuPrice())
                .menuStatus(menu.getMenuStatus())
                .menuTotalPrice(menuTotalPrice).build();
    }
}
