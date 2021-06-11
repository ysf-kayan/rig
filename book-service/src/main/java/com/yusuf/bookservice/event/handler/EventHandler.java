package com.yusuf.bookservice.event.handler;

import com.yusuf.bookservice.dto.BookDto;
import com.yusuf.bookservice.entity.Book;
import com.yusuf.bookservice.event.OrderApprovedEvent;
import com.yusuf.bookservice.event.OrderCreatedEvent;
import com.yusuf.bookservice.event.OrderRejectedEvent;
import com.yusuf.bookservice.event.sender.EventSender;
import com.yusuf.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventHandler {
    private final BookService bookService;
    private final EventSender eventSender;

    @Value("${bs.order_created.queue.name}")
    private String orderCreatedQueue;

    @RabbitListener(queues = "#{orderCreatedQueue}")
    public void onOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        List<BookDto> books = new ArrayList<>();

        orderCreatedEvent.getBookIds().forEach(id -> {
            BookDto b = bookService.findById(id);
            if (b != null) {
                books.add(b);
            }
        });

        boolean orderApproved = false;
        try {
            bookService.approveOrRejectOrder(orderCreatedEvent);
            orderApproved = true;
        } catch (Exception e) {
            // do nothing
        }

        if (orderApproved) {
            OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent();
            orderApprovedEvent.setOrderId(orderCreatedEvent.getOrderId());
            orderApprovedEvent.setBooks(books);
            eventSender.sendOrderApprovedEvent(orderApprovedEvent);
        } else {
            OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent();
            orderRejectedEvent.setOrderId(orderCreatedEvent.getOrderId());
            orderRejectedEvent.setBooks(books);
            eventSender.sendOrderRejectedEvent(orderRejectedEvent);
        }
    }
}
