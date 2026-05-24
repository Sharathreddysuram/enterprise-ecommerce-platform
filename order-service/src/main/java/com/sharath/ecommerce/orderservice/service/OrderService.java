package com.sharath.ecommerce.orderservice.service;

import com.sharath.ecommerce.orderservice.client.PaymentClient;
import com.sharath.ecommerce.orderservice.client.ProductClient;
import com.sharath.ecommerce.orderservice.dto.OrderRequest;
import com.sharath.ecommerce.orderservice.dto.OrderResponse;
import com.sharath.ecommerce.orderservice.dto.PaymentRequest;
import com.sharath.ecommerce.orderservice.dto.PaymentResponse;
import com.sharath.ecommerce.orderservice.dto.ProductResponse;
import com.sharath.ecommerce.orderservice.entity.Order;
import com.sharath.ecommerce.orderservice.event.OrderEvent;
import com.sharath.ecommerce.orderservice.producer.OrderProducer;
import com.sharath.ecommerce.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderProducer producer;

    public OrderService(
            OrderRepository repository,
            ProductClient productClient,
            PaymentClient paymentClient,
            OrderProducer producer
    ) {
        this.repository = repository;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
        this.producer = producer;
    }

    public OrderResponse placeOrder(OrderRequest request) {

        ProductResponse product = productClient.getProductById(request.getProductId());

        if (product.getQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient product quantity");
        }

        Double totalAmount = product.getPrice() * request.getQuantity();

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = repository.save(order);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(savedOrder.getId());
        paymentRequest.setAmount(totalAmount);

        PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest);

        if ("SUCCESS".equals(paymentResponse.getPaymentStatus())) {
            savedOrder.setOrderStatus("CONFIRMED");
        } else {
            savedOrder.setOrderStatus("FAILED");
        }

        Order updatedOrder = repository.save(savedOrder);

        OrderEvent event = new OrderEvent();
        event.setOrderId(updatedOrder.getId());
        event.setProductId(updatedOrder.getProductId());
        event.setQuantity(updatedOrder.getQuantity());
        event.setTotalAmount(updatedOrder.getTotalAmount());
        event.setOrderStatus(updatedOrder.getOrderStatus());

        producer.sendOrderEvent(event);

        return mapToResponse(updatedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        return mapToResponse(order);
    }

    public void cancelOrder(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.setOrderStatus("CANCELLED");
        repository.save(order);
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setProductId(order.getProductId());
        response.setQuantity(order.getQuantity());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderStatus(order.getOrderStatus());
        response.setOrderDate(order.getOrderDate());
        return response;
    }
}