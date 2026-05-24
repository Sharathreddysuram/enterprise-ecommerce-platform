package com.sharath.ecommerce.orderservice.controller;

import com.sharath.ecommerce.orderservice.dto.OrderRequest;
import com.sharath.ecommerce.orderservice.dto.OrderResponse;
import com.sharath.ecommerce.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public OrderResponse placeOrder(@RequestBody OrderRequest request) {
        return service.placeOrder(request);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    @PutMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        service.cancelOrder(id);
        return "Order cancelled successfully with id: " + id;
    }
}