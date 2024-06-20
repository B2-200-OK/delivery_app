package com.sparta.delivery_app.domain.order.repository;

import com.sparta.delivery_app.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
