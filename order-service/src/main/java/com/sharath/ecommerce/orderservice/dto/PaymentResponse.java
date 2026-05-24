package com.sharath.ecommerce.orderservice.dto;

public class PaymentResponse {

    private String paymentStatus;

    public PaymentResponse() {
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}