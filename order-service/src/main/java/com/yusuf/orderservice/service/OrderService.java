package com.yusuf.orderservice.service;

import com.yusuf.orderservice.dto.BookDto;
import com.yusuf.orderservice.dto.OrderDto;
import com.yusuf.orderservice.event.OrderApprovedEvent;
import com.yusuf.orderservice.event.OrderRejectedEvent;

import java.util.Date;
import java.util.List;

public interface OrderService {
    OrderDto get(Long id);

    OrderDto save(OrderDto bookDto);

    List<OrderDto> getByDateInterval(Date startDate, Date endDate);

    List<OrderDto> getOrdersOfUserByDateInterval(Long customerId, Date startDate, Date endDate);

    void saveBooksOfOrder(Long orderId, List<BookDto> orderBooks);

    void handleOrderApproved(OrderApprovedEvent orderApprovedEvent);

    void handleOrderRejected(OrderRejectedEvent orderRejectedEvent);
}
