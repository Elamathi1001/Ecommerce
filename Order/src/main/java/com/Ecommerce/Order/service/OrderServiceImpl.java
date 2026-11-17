package com.Ecommerce.Order.service;

import com.Ecommerce.Order.dto.OrderRequest;
import com.Ecommerce.Order.dto.OrderResponse;
import com.Ecommerce.Order.entity.Order;
import com.Ecommerce.Order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private static final Set<String> VALID_STATUSES =
            Set.of("PLACED", "CONFIRMED", "SHIPPED", "DELIVERED");

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Create Order
    @Override
    public OrderResponse createOrder(OrderRequest request) {

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    // Get Order By ID
    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with ID: " + orderId
                ));

        return mapToResponse(order);
    }

    // Get All Orders
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get Orders By Customer ID
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Update Order Status
    @Override
    public OrderResponse updateOrderStatus(
            Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with ID: " + orderId
                ));

        if (!VALID_STATUSES.contains(status)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order status"
            );
        }

        if ("CANCELLED".equals(order.getOrderStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cancelled order cannot be updated"
            );
        }

        order.setOrderStatus(status);

        Order updatedOrder = orderRepository.save(order);

        return mapToResponse(updatedOrder);
    }

    // Cancel Order
    @Override
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found with ID: " + orderId
                ));

        if ("DELIVERED".equals(order.getOrderStatus())
                || "CANCELLED".equals(order.getOrderStatus())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This order cannot be cancelled"
            );
        }

        order.setOrderStatus("CANCELLED");

        orderRepository.save(order);
    }

    // Convert Entity into Response DTO
    private OrderResponse mapToResponse(Order order) {

        OrderResponse response = new OrderResponse();

        response.setOrderId(order.getOrderId());
        response.setCustomerId(order.getCustomerId());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderStatus(order.getOrderStatus());
        response.setOrderDate(order.getOrderDate());

        return response;
    }
}
