package com.yusuf.orderservice.service.impl;

import com.yusuf.orderservice.dto.BookDto;
import com.yusuf.orderservice.dto.OrderDto;
import com.yusuf.orderservice.dto.UserMonthlyStatistics;
import com.yusuf.orderservice.service.OrderService;
import com.yusuf.orderservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final OrderService orderService;

    @Override
    public HashMap<Integer, UserMonthlyStatistics> getUserMonthlyStatistics(Long userId) {
        HashMap<Integer, UserMonthlyStatistics> statisticsMap = new HashMap<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);

        Calendar january = Calendar.getInstance();
        january.set(year, Calendar.JANUARY, 1, 0, 0, 0);

        for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++) {
            Calendar month = Calendar.getInstance();
            month.setTime(january.getTime());
            month.add(Calendar.MONTH, i);
            Calendar nextMonth = Calendar.getInstance();
            nextMonth.setTime(january.getTime());
            nextMonth.add(Calendar.MONTH, i + 1);

            List<OrderDto> orderDtos =
                    orderService.getOrdersOfUserByDateInterval(userId, month.getTime(), nextMonth.getTime());
            Double monthlyTotalAmount = 0.0;
            int monthlyBookCount = 0;
            for (OrderDto orderDto : orderDtos) {
                monthlyTotalAmount += orderDto.getTotalPurchasedAmount();
                monthlyBookCount += orderDto.getBooks().size();
            }

            UserMonthlyStatistics userMonthlyStatistics = new UserMonthlyStatistics();
            userMonthlyStatistics.setTotalOrderCount(orderDtos.size());
            userMonthlyStatistics.setTotalPurchasedAmount(monthlyTotalAmount);
            userMonthlyStatistics.setTotalBookCount(monthlyBookCount);

            statisticsMap.put(i, userMonthlyStatistics);
        }

        return statisticsMap;
    }
}
