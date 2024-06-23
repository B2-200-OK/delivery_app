package com.sparta.delivery_app.domain.order.adaptor;

import com.sparta.delivery_app.common.exception.errorcode.OrderErrorCode;
import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.OrderAccessDeniedException;
import com.sparta.delivery_app.common.globalcustomexception.OrderNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.ReviewNotFoundException;
import com.sparta.delivery_app.domain.order.entity.Order;
import com.sparta.delivery_app.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderAdaptor {

    private final OrderRepository orderRepository;

    /**
     * 주문 저장
     */
    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    /**
     *  주문 아이디 검증
     */
    public Order queryOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(OrderErrorCode.ORDER_NOT_FOUND));
    }

    /**
     * 해당 유저의 주문인지 검증
     */
    public Order queryOrderByIdAndUserID(Long userId, Long orderId) {
        Order order = queryOrderById(orderId);
        if (!order.getUser().getId().equals(userId)) {
            throw new OrderAccessDeniedException(OrderErrorCode.ORDER_ACCESS_DENIED);
        }
        return order;
    }

    /**
     * 해당 유저의 모든 주문 조회
     */
    public Page<Order> queryOrdersByUserId(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(pageable, userId);
    }

    public Long queryReviewIdByOrderId(Long orderId) {
        return orderRepository.findReviewIdById(orderId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }
}
