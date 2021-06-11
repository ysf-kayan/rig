package com.yusuf.orderservice.event.handler;

import com.yusuf.orderservice.event.OrderApprovedEvent;
import com.yusuf.orderservice.event.OrderCreatedEvent;
import com.yusuf.orderservice.event.OrderRejectedEvent;
import com.yusuf.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventHandler {

    private final OrderService orderService;

    @Value("${bs.order_rejected.queue.name}")
    private String orderRejectedQueue;

    @RabbitListener(queues = "#{orderApprovedQueue}")
    public void onOrderApprovedEvent(OrderApprovedEvent orderApprovedEvent) {
        orderService.saveBooksOfOrder(orderApprovedEvent.getOrderId(), orderApprovedEvent.getBooks());
        orderService.handleOrderApproved(orderApprovedEvent);
    }

    @RabbitListener(queues = "#{orderRejectedQueue}")
    public void onOrderRejectedEvent(OrderRejectedEvent orderRejectedEvent) {
        orderService.saveBooksOfOrder(orderRejectedEvent.getOrderId(), orderRejectedEvent.getBooks());
        orderService.handleOrderRejected(orderRejectedEvent);
    }
}
