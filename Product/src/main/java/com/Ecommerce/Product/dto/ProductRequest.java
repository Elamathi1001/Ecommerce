package com.Ecommerce.Product.dto;




import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String productName;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String category;

    private String brand;

    private String imageUrl;
}

