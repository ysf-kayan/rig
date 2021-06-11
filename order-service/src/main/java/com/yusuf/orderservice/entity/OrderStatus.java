package com.yusuf.orderservice.entity;

public enum OrderStatus {

    PENDING_APPROVAL(0),
    APPROVED(1),
    REJECTED(2);

    private final int val;

    OrderStatus(int val) {
        this.val = val;
    }

    public int getValue() {
        return val;
    }
}
