package com.yusuf.orderservice.event;

import com.yusuf.orderservice.dto.BookDto;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

public class OrderApprovedEvent {
    @Getter
    private final String eventId = UUID.randomUUID().toString();

    @Getter
    private Long orderId;

    @Getter
    private List<BookDto> books;
}
