package com.yusuf.orderservice.repo;

import com.yusuf.orderservice.entity.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
