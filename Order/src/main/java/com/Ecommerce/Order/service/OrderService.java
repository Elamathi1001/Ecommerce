package com.Ecommerce.Order.service;

import com.Ecommerce.Order.dto.OrderRequest;
import com.Ecommerce.Order.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByCustomerId(Long customerId);

    OrderResponse updateOrderStatus(Long orderId, String status);

    void cancelOrder(Long orderId);
}
