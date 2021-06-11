package com.yusuf.bookservice.entity;

public class BookStockException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Stock update failed.";
    }
}
