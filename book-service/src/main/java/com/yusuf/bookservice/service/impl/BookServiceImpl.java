package com.yusuf.bookservice.service.impl;

import com.yusuf.bookservice.dto.BookDto;
import com.yusuf.bookservice.entity.Book;
import com.yusuf.bookservice.event.OrderCreatedEvent;
import com.yusuf.bookservice.entity.BookStockException;
import com.yusuf.bookservice.repo.BookRepository;
import com.yusuf.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    @Override
    public BookDto save(BookDto bookDto) {
        Book book = new Book(
            bookDto.getName(),
            bookDto.getAuthor(),
            bookDto.getStock(),
            bookDto.getPrice()
        );

        bookRepository.save(book);

        bookDto.setId(book.getId());

        return bookDto;
    }

    @Override
    @Transactional
    public BookDto updateStock(Long bookId, Integer stock) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setStock(stock);
        bookRepository.save(book);
        return BookDto.createFrom(book);
    }

    @Override
    @Transactional
    public void approveOrRejectOrder(OrderCreatedEvent orderCreatedEvent) {
        int numOfBooks = orderCreatedEvent.getBookIds().size();

        int numOfSuccessfulStockUpdates = 0;

        for (Long bookId : orderCreatedEvent.getBookIds()) {
            Query query = entityManager.createNamedQuery("updateBookStock");
            query.setParameter("bookId", bookId);
            numOfSuccessfulStockUpdates += query.executeUpdate();
        }

        // If these two values is not equal that means at least one of the book ids is invalid
        // or at least one of the books has insufficient stock.
        // Throwing an exception will revert the transaction
        if (numOfBooks != numOfSuccessfulStockUpdates) {
            throw new BookStockException();
        }
    }

    @Override
    public BookDto findById(Long id) {
        BookDto bookDto = null;

        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            bookDto = BookDto.createFrom(book);
        }

        return bookDto;
    }

    @Override
    public List<BookDto> findAllById(Iterable<Long> ids) {
        List<BookDto> dtos = new ArrayList<>();
        bookRepository.findAllById(ids).forEach(book -> {
            dtos.add(BookDto.createFrom(book));
        });
        return dtos;
    }
}


