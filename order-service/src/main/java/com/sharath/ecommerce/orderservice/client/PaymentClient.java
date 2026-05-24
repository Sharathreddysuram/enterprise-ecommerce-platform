package com.sharath.ecommerce.orderservice.client;

import com.sharath.ecommerce.orderservice.dto.PaymentRequest;
import com.sharath.ecommerce.orderservice.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments")
    PaymentResponse processPayment(PaymentRequest request);
}