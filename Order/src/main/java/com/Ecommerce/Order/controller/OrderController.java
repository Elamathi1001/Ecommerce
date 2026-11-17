package com.Ecommerce.Order.controller;

import com.Ecommerce.Order.dto.OrderRequest;
import com.Ecommerce.Order.dto.OrderResponse;
import com.Ecommerce.Order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create Order
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request) {

        OrderResponse response =
                orderService.createOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get Order By ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId)
        );
    }

    // Get All Orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }

    // Get Orders By Customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>>
    getOrdersByCustomerId(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                orderService.getOrdersByCustomerId(customerId)
        );
    }

    // Update Order Status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status)
        );
    }

    // Cancel Order
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.noContent().build();
    }
}