package com.yusuf.bookservice.controller;

import com.yusuf.bookservice.dto.BookDto;
import com.yusuf.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> save(@Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.save(bookDto));
    }

    @PatchMapping("/updateStock/{bookId}/{stockVal}")
    public ResponseEntity<BookDto> updateStock(@PathVariable @Min(1) Long bookId,
                                               @PathVariable @Min(1) Integer stockVal) {
        return ResponseEntity.ok(bookService.updateStock(bookId, stockVal));
    }

}
