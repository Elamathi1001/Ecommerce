package com.Ecommerce.Order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponse {

    private Long orderId;

    private Long customerId;

    private BigDecimal totalAmount;

    private String orderStatus;

    private LocalDateTime orderDate;
}
