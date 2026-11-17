package com.Ecommerce.Payment.service;



import com.Ecommerce.Payment.dto.PaymentRequest;
import com.Ecommerce.Payment.dto.PaymentResponse;
import com.Ecommerce.Payment.entity.Payment;
import com.Ecommerce.Payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private static final Set<String> VALID_STATUSES =
            Set.of("PENDING", "SUCCESS", "FAILED", "REFUNDED");

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setOrderId(request.getOrderId());
        payment.setCustomerId(request.getCustomerId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setPaymentStatus("PENDING");
        payment.setTransactionId("TXN-" + UUID.randomUUID());
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found: " + paymentId));

        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> getPaymentsByCustomerId(Long customerId) {

        return paymentRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, String status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found: " + paymentId));

        String updatedStatus = status.toUpperCase();

        if (!VALID_STATUSES.contains(updatedStatus)) {
            throw new IllegalArgumentException(
                    "Invalid payment status: " + status);
        }

        payment.setPaymentStatus(updatedStatus);

        Payment updatedPayment = paymentRepository.save(payment);

        return mapToResponse(updatedPayment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(payment.getPaymentId());
        response.setOrderId(payment.getOrderId());
        response.setCustomerId(payment.getCustomerId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setTransactionId(payment.getTransactionId());
        response.setPaymentDate(payment.getPaymentDate());

        return response;
    }
}
