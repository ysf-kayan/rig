package com.yusuf.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yusuf.orderservice.entity.Order;
import com.yusuf.orderservice.entity.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long orderId;

    @NotNull
    private Long customerId;

    @NotEmpty
    private String customerName;

    @NotEmpty
    private List<Long> bookIds;

    private List<BookDto> books;

    private OrderStatus status;

    private LocalDateTime created;

    public Double getTotalPurchasedAmount() {
        Double amount = 0.0;

        if (books != null) {
            for (BookDto bookDto : books) {
                amount += bookDto.getPrice();
            }
        }

        return amount;
    }

    public static OrderDto createFrom(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(order.getCustomerId());
        orderDto.setCustomerName(order.getCustomerName());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderId(order.getId());
        orderDto.setCreated(order.getCreated());

        List<BookDto> orderBooks = new ArrayList<>();
        order.getBooks().forEach(book -> {
            BookDto bookDto = new BookDto();
            bookDto.setName(book.getBookName());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setPrice(book.getPrice());
            orderBooks.add(bookDto);
        });
        orderDto.setBooks(orderBooks);

        return orderDto;
    }
}
