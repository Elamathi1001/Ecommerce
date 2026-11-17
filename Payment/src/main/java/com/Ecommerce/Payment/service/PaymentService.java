package com.Ecommerce.Payment.service;

import com.Ecommerce.Payment.dto.PaymentRequest;
import com.Ecommerce.Payment.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long paymentId);

    List<PaymentResponse> getAllPayments();

    List<PaymentResponse> getPaymentsByOrderId(Long orderId);

    List<PaymentResponse> getPaymentsByCustomerId(Long customerId);

    PaymentResponse updatePaymentStatus(Long paymentId, String status);
}
