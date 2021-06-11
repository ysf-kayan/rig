package com.yusuf.orderservice.config;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Getter
    @Value("${bs.order.exchange.name}")
    private String orderExchange;

    @Getter
    @Value("${bs.order_created.queue.name}")
    private String orderCreatedQueue;

    @Getter
    @Value("${bs.order_rejected.queue.name}")
    private String orderRejectedQueue;

    @Getter
    @Value("${bs.order_approved.queue.name}")
    private String orderApprovedQueue;

    @Bean
    TopicExchange orderExchange() {
        return new TopicExchange(orderExchange);
    }

    @Bean
    Queue orderCreatedQueue() {
        return new Queue(orderCreatedQueue, true);
    }

    @Bean
    Queue orderApprovedQueue() {
        return new Queue(orderApprovedQueue, true);
    }

    @Bean
    Queue orderRejectedQueue() {
        return new Queue(orderRejectedQueue, true);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(orderCreatedQueue()).to(orderExchange()).with(orderCreatedQueue().getName());
    }

    @Bean
    Binding orderApprovedBinding() {
        return BindingBuilder.bind(orderApprovedQueue()).to(orderExchange()).with(orderApprovedQueue().getName());
    }

    @Bean
    Binding orderRejectedBinding() {
        return BindingBuilder.bind(orderRejectedQueue()).to(orderExchange()).with(orderRejectedQueue().getName());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
