package com.Ecommerce.customer.repository;

import com.Ecommerce.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);
}
