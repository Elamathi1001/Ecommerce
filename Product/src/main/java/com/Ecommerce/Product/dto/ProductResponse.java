package com.Ecommerce.Product.dto;



import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long productId;

    private String productName;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String category;

    private String brand;

    private String imageUrl;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

