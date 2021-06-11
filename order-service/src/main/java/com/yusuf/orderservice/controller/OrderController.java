package com.yusuf.orderservice.controller;

import com.yusuf.orderservice.dto.OrderDto;
import com.yusuf.orderservice.event.OrderCreatedEvent;
import com.yusuf.orderservice.event.sender.EventSender;
import com.yusuf.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> get(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PostMapping
    public ResponseEntity<OrderDto> save(@RequestBody @Validated OrderDto orderDto) {
        return ResponseEntity.ok(orderService.save(orderDto));
    }

    @GetMapping("/betweenDates/{startDate}/{endDate}")
    public ResponseEntity<List<OrderDto>> getByDateInterval(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(orderService.getByDateInterval(startDate, endDate));
    }
}
