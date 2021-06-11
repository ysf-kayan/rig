package com.yusuf.orderservice.event.sender;

import com.yusuf.orderservice.config.RabbitMqConfiguration;
import com.yusuf.orderservice.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventSender {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfiguration rabbitMqConfiguration;

    public EventSender(RabbitTemplate rabbitTemplate, RabbitMqConfiguration rabbitMqConfiguration) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMqConfiguration = rabbitMqConfiguration;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(rabbitMqConfiguration.getOrderExchange(),
                rabbitMqConfiguration.getOrderCreatedQueue(), event);
    }
}
