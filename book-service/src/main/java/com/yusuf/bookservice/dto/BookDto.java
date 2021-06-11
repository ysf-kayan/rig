package com.yusuf.bookservice.dto;

import com.yusuf.bookservice.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;

    @NotEmpty(message = "Every book has a name!")
    private String name;

    @NotEmpty(message = "Every book has an author!")
    private String author;

    @Min(value = 1, message = "Stock value can not be less than 1!")
    private Integer stock;

    @Min(value = 0, message = "Price can not be negative!")
    private Double price;

    public static BookDto createFrom(Book book) {
        return new BookDto(
                book.getId(),
                book.getName(),
                book.getAuthor(),
                book.getStock(),
                book.getPrice()
        );
    }
}
