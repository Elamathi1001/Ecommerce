package com.Ecommerce.Payment.dto;



import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private Long orderId;

    private Long customerId;

    private BigDecimal amount;

    private String paymentMethod;
}

