package com.yusuf.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private Long id;

    private String name;

    private String author;

    private Double price;
}
