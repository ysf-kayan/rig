package com.yusuf.bookservice.service;

import com.yusuf.bookservice.dto.BookDto;
import com.yusuf.bookservice.event.OrderCreatedEvent;

import java.util.List;

public interface BookService {
    BookDto save(BookDto bookDto);

    BookDto updateStock(Long bookId, Integer stock);

    void approveOrRejectOrder(OrderCreatedEvent orderCreatedEvent);

    BookDto findById(Long id);

    List<BookDto> findAllById(Iterable<Long> ids);
}
