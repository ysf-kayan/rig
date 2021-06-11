package com.yusuf.orderservice.controller;

import com.yusuf.orderservice.dto.UserMonthlyStatistics;
import com.yusuf.orderservice.service.OrderService;
import com.yusuf.orderservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/{customerId}")
    public ResponseEntity<HashMap<Integer, UserMonthlyStatistics>> getUserMonthlyStatistics(@PathVariable Long customerId) {
        return ResponseEntity.ok(statisticsService.getUserMonthlyStatistics(customerId));
    }

    @GetMapping
    public String test() {
        return "StatisticsController test()";
    }

}
