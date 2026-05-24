package com.sharath.ecommerce.paymentservice.controller;

import com.sharath.ecommerce.paymentservice.dto.PaymentRequest;
import com.sharath.ecommerce.paymentservice.dto.PaymentResponse;
import com.sharath.ecommerce.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public PaymentResponse makePayment(@Valid @RequestBody PaymentRequest request) {
        return service.makePayment(request);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id);
    }
}