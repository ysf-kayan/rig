package com.yusuf.bookservice.entity;
import lombok.*;

import javax.persistence.*;

@NamedQueries(
    // Using native query to easily evade database race conditions
    @NamedQuery(
        name = "updateBookStock",
        query = "update Book b set b.stock=b.stock-1 where b.id=:bookId and b.stock > 0"
    )
)

@Entity
@Table(name = "book")
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @SequenceGenerator(name = "seq_book", allocationSize = 1)
    @GeneratedValue(generator = "seq_book", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String author;

    private int stock;

    @Version
    @Column(name = "optlock")
    private long version;

    // Note: Using double for money is actually bad, but, what can i do sometimes :)
    private Double price;

    public Book(String name, String author, int stock, Double price) {
        this.name = name;
        this.author = author;
        this.stock = stock;
        this.price = price;
    }
}
