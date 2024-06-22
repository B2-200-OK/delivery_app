package com.sparta.delivery_app.domain.order.service;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.StoreMenuMismatchException;
import com.sparta.delivery_app.common.globalcustomexception.TotalPriceException;
import com.sparta.delivery_app.common.security.AuthenticationUser;
import com.sparta.delivery_app.domain.commen.page.util.PageUtil;
import com.sparta.delivery_app.domain.menu.adaptor.MenuAdaptor;
import com.sparta.delivery_app.domain.menu.entity.Menu;
import com.sparta.delivery_app.domain.menu.entity.MenuStatus;
import com.sparta.delivery_app.domain.order.adaptor.OrderAdaptor;
import com.sparta.delivery_app.domain.order.dto.request.MenuItemRequestDto;
import com.sparta.delivery_app.domain.order.dto.request.OrderAddRequestDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderAddResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderGetResponseDto;
import com.sparta.delivery_app.domain.order.dto.response.OrderPageResponseDto;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.entity.OrderItem;
import com.sparta.delivery_app.domain.order.entity.OrderStatus;
import com.sparta.delivery_app.domain.store.adaptor.StoreAdaptor;
import com.sparta.delivery_app.domain.store.entity.Store;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderAdaptor orderAdaptor;
    private UserAdaptor userAdaptor;
    private StoreAdaptor storeAdaptor;
    private MenuAdaptor menuAdaptor;

    /**
     * 주문 생성
     * @param authenticationUser 인증된 유저 정보
     * @param requestDto 주문 정보
     * @return OrderAddResponseDto 생성된 주문 정보
     */
    public OrderAddResponseDto addOrder(AuthenticationUser authenticationUser, final OrderAddRequestDto requestDto) {
        User user = userAdaptor.queryUserByEmail(authenticationUser.getUsername());
        Store store = storeAdaptor.queryStoreById(requestDto.storeId());
        Long totalPrice;

        Order currentOrder = Order.builder()
                .user(user)
                .store(store)
                .orderStatus(OrderStatus.ORDER_COMPLETED)
                .build();

        addValidatedMenuItemsToOrder(currentOrder, requestDto.menuList());
        totalPrice = currentOrder.calculateTotalPrice();

        if (totalPrice < store.getMinTotalPrice()) {
            throw new TotalPriceException(OrderErrorCode.TOTAL_PRICE_ERROR);
        }

        orderAdaptor.saveOrder(currentOrder);
        return OrderAddResponseDto.of(currentOrder, totalPrice);
    }

    /**
     * 주문 단건 조회
     * @param authenticationUser 인증된 유저 정보
     * @param orderId 조회할 주문 아이디
     * @return OrderGetResponseDto 조회된 주문 정보
     */
    public OrderGetResponseDto findOrder(AuthenticationUser authenticationUser, Long orderId) {
        User user = userAdaptor.queryUserByEmail(authenticationUser.getUsername());
        Order order = orderAdaptor.queryOrderByIdAndUserID(user.getId(), orderId);
        return OrderGetResponseDto.of(order);
    }

    /**
     * 주문 전체 조회 (페이징)
     * @param authenticationUser 인증된 유저 정보
     * @param pageNum 접근할 페이지 번호
     * @param sortBy 정렬 조건
     * @param isDesc 내림차순 여부
     * @return OrderPageResponseDto 조회된 페이지
     */
    public OrderPageResponseDto findOrders(
            AuthenticationUser authenticationUser,
            Integer pageNum,
            String sortBy,
            Boolean isDesc
    ) {
        User user = userAdaptor.queryUserByEmail(authenticationUser.getUsername());

        Pageable pageable = PageUtil.createPageable(pageNum, PageUtil.PAGE_SIZE_FIVE, sortBy, isDesc);

        Page<Order> orderPage = orderAdaptor.queryOrdersByUserId(pageable, user.getId());

        PageUtil.validatePage(pageNum, orderPage);
        return OrderPageResponseDto.of(pageNum, orderPage);
    }

    /**
     * 선택한 메뉴들을 현재 주문에 추가
     * @param currentOrder 현재 주문
     * @param menuItemRequestDtoList 선택한 메뉴
     */
    private void addValidatedMenuItemsToOrder(Order currentOrder, List<MenuItemRequestDto> menuItemRequestDtoList) {
        for (MenuItemRequestDto menuItemRequestDto : menuItemRequestDtoList) {
            Long menuId = menuItemRequestDto.menuId();
            Integer quantity = menuItemRequestDto.quantity();

            Menu menu = menuAdaptor.queryMenuById(menuId);

            if (menu.getStore() != currentOrder.getStore()) {
                throw new StoreMenuMismatchException(OrderErrorCode.STORE_MENU_MISMATCH);
            }

            MenuStatus.checkMenuStatus(menu);

            OrderItem orderItem = OrderItem.builder()
                    .order(currentOrder)
                    .menu(menu)
                    .quantity(quantity)
                    .build();

            currentOrder.addOrderItem(orderItem);
        }
    }
}
