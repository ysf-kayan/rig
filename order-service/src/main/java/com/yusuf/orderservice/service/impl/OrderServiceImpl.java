package com.yusuf.orderservice.service.impl;

import com.yusuf.orderservice.dto.BookDto;
import com.yusuf.orderservice.dto.OrderDto;
import com.yusuf.orderservice.entity.Order;
import com.yusuf.orderservice.entity.OrderBook;
import com.yusuf.orderservice.entity.OrderStatus;
import com.yusuf.orderservice.event.OrderApprovedEvent;
import com.yusuf.orderservice.event.OrderCreatedEvent;
import com.yusuf.orderservice.event.OrderRejectedEvent;
import com.yusuf.orderservice.event.sender.EventSender;
import com.yusuf.orderservice.repo.OrderBookRepository;
import com.yusuf.orderservice.repo.OrderRepository;
import com.yusuf.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderBookRepository orderBookRepository;
    private final EventSender eventSender;

    @Override
    public OrderDto get(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return OrderDto.createFrom(order);
    }

    @Override
    public List<OrderDto> getByDateInterval(Date startDate, Date endDate) {
        List<Order> orders = orderRepository.findByDateInterval(
                new java.sql.Timestamp(startDate.getTime()).toLocalDateTime(),
                new java.sql.Timestamp(endDate.getTime()).toLocalDateTime()
        );
        List<OrderDto> orderDtos = new ArrayList<>();

        orders.forEach(order -> {
            orderDtos.add(OrderDto.createFrom(order));
        });

        return orderDtos;
    }

    @Override
    public List<OrderDto> getOrdersOfUserByDateInterval(Long customerId, Date startDate, Date endDate) {
        List<Order> orders = orderRepository.findOrdersOfUserByDateInterval(
                customerId,
                new java.sql.Timestamp(startDate.getTime()).toLocalDateTime(),
                new java.sql.Timestamp(endDate.getTime()).toLocalDateTime()
        );
        List<OrderDto> orderDtos = new ArrayList<>();

        orders.forEach(order -> {
            orderDtos.add(OrderDto.createFrom(order));
        });

        return orderDtos;
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerId(orderDto.getCustomerId());
        order.setCustomerName(orderDto.getCustomerName());
        order.setStatus(OrderStatus.PENDING_APPROVAL);
        orderRepository.save(order);

        orderDto.setOrderId(order.getId());
        orderDto.setStatus(order.getStatus());
        orderDto.setCreated(order.getCreated());

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(order.getId());
        orderCreatedEvent.setBookIds(orderDto.getBookIds());
        eventSender.sendOrderCreatedEvent(orderCreatedEvent);

        return orderDto;
    }

    @Override
    @Transactional
    public void handleOrderApproved(OrderApprovedEvent orderApprovedEvent) {
        Order order = orderRepository.findById(orderApprovedEvent.getOrderId()).orElse(null);

        if (order != null) {
            order.setStatus(OrderStatus.APPROVED);
            order.setFinalized(LocalDateTime.now());
            orderRepository.save(order);
        }
    }

    @Override
    @Transactional
    public void handleOrderRejected(OrderRejectedEvent orderRejectedEvent) {
        Order order = orderRepository.findById(orderRejectedEvent.getOrderId()).orElse(null);

        if (order != null) {
            order.setStatus(OrderStatus.REJECTED);
            order.setFinalized(LocalDateTime.now());
            orderRepository.save(order);
        }
    }

    @Override
    @Transactional
    public void saveBooksOfOrder(Long orderId, List<BookDto> orderBooks) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null) {
            orderBooks.forEach(bookDto -> {
                OrderBook orderBook = new OrderBook(
                    bookDto.getId(),
                    bookDto.getName(),
                    bookDto.getAuthor(),
                    bookDto.getPrice(),
                    order
                );
                orderBookRepository.save(orderBook);
            });
        }
    }
}
