package com.Ecommerce.Payment.repository;

import com.Ecommerce.Payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByCustomerId(Long customerId);
}
