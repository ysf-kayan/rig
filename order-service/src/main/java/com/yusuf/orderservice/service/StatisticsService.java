package com.yusuf.orderservice.service;

import com.yusuf.orderservice.dto.UserMonthlyStatistics;

import java.util.HashMap;

public interface StatisticsService {
    HashMap<Integer, UserMonthlyStatistics> getUserMonthlyStatistics(Long userId);
}
