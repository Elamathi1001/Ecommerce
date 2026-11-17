package com.Ecommerce.customer.dto;



import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerResponse {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
