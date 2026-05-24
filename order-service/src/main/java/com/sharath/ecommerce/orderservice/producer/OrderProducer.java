package com.sharath.ecommerce.orderservice.producer;

import com.sharath.ecommerce.orderservice.event.OrderEvent;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    public void sendOrderEvent(OrderEvent event) {

        System.out.println("Order Event Sent: " + event.getOrderId());
    }
}