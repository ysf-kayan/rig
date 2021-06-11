package com.yusuf.orderservice.dto;

import lombok.Data;

@Data
public class UserMonthlyStatistics {
    private int totalOrderCount;
    private int totalBookCount;
    private Double totalPurchasedAmount;
}
