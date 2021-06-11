package com.yusuf.orderservice.event;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public class OrderCreatedEvent {
    @Getter
    private String eventId = UUID.randomUUID().toString();

    @Getter
    @Setter
    private Long orderId;

    @Getter
    @Setter
    private List<Long> bookIds;
}
