package com.sharath.ecommerce.orderservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharath.ecommerce.orderservice.event.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String orderEventsTopic;

    public OrderProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper,
            @Value("${app.kafka.topic.order-events:order-events}") String orderEventsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.orderEventsTopic = orderEventsTopic;
    }

    public void sendOrderEvent(OrderEvent event) {

        try {
            String payload = objectMapper.writeValueAsString(event);
            String key = event.getOrderId() != null ? String.valueOf(event.getOrderId()) : null;

            kafkaTemplate.send(orderEventsTopic, key, payload)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to publish order event for order {}: {}",
                                    event.getOrderId(), ex.getMessage());
                        } else {
                            log.info("Order event published for order {} to topic {} (partition {}, offset {})",
                                    event.getOrderId(), orderEventsTopic,
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize order event for order {}: {}",
                    event.getOrderId(), e.getMessage());
        }
    }
}
