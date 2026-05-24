package com.sharath.ecommerce.paymentservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private Long orderId;
    private Double amount;
    private String paymentMode;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime paymentDate;
}