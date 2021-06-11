package com.yusuf.orderservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "os_order")
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @SequenceGenerator(name = "os_seq_order", allocationSize = 1)
    @GeneratedValue(generator = "os_seq_order", strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long customerId;

    private String customerName;

    @OneToMany
    @JoinColumn(name = "os_order_orderbook")
    private List<OrderBook> books;

    private OrderStatus status;

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime finalized;
}
