package com.yusuf.bookservice.event;

import com.yusuf.bookservice.dto.BookDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public class OrderApprovedEvent {
    @Getter
    private final String eventId = UUID.randomUUID().toString();

    @Getter
    @Setter
    private Long orderId;

    @Getter
    @Setter
    private List<BookDto> books;
}
