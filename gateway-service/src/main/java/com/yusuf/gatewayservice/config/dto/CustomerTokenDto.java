package com.yusuf.gatewayservice.config.dto;

import lombok.Data;

@Data
public class CustomerTokenDto {
    private String username;
    private String token;
}