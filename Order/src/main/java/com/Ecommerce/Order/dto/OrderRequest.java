package com.Ecommerce.Order.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderRequest {

    private Long customerId;

    private BigDecimal totalAmount;
}
