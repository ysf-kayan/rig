package com.yusuf.orderservice.entity;

import com.yusuf.orderservice.dto.BookDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "os_orderbook")
@NoArgsConstructor
@Getter
@Setter
public class OrderBook {

    @Id
    @SequenceGenerator(name = "os_seq_order_book", allocationSize = 1)
    @GeneratedValue(generator = "os_seq_order_book", strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long bookId;

    private String bookName;

    private String author;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "os_order_orderbook")
    private Order order;

    public OrderBook(Long bookId, String bookName, String author, Double price, Order order) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.order = order;
    }
}
