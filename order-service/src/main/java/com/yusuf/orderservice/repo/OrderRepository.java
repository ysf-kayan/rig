package com.yusuf.orderservice.repo;

import com.yusuf.orderservice.dto.UserMonthlyStatistics;
import com.yusuf.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.created > ?1 and o.created < ?2 order by o.created asc")
    List<Order> findByDateInterval(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select o from Order o " +
            "where o.customerId = ?1 and o.created > ?2 and o.created < ?3 " +
            "and o.status = com.yusuf.orderservice.entity.OrderStatus.APPROVED " +
            "order by o.created asc")
    List<Order> findOrdersOfUserByDateInterval(Long customerId, LocalDateTime startDate, LocalDateTime endDate);
}
