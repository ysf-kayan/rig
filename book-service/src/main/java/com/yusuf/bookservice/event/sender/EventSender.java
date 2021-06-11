package com.yusuf.bookservice.event.sender;

import com.yusuf.bookservice.config.RabbitMqConfiguration;
import com.yusuf.bookservice.event.OrderApprovedEvent;
import com.yusuf.bookservice.event.OrderCreatedEvent;
import com.yusuf.bookservice.event.OrderRejectedEvent;
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

    public void sendOrderApprovedEvent(OrderApprovedEvent event) {
        rabbitTemplate.convertAndSend(rabbitMqConfiguration.getOrderExchange(),
                rabbitMqConfiguration.getOrderApprovedQueue(), event);
    }

    public void sendOrderRejectedEvent(OrderRejectedEvent event) {
        rabbitTemplate.convertAndSend(rabbitMqConfiguration.getOrderExchange(),
                rabbitMqConfiguration.getOrderRejectedQueue(), event);
    }
}
