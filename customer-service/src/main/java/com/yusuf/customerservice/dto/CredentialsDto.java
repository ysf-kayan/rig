package com.yusuf.customerservice.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CredentialsDto {
    @NotEmpty(message = "Username can't be empty!")
    private String username;

    @NotEmpty(message = "Password can't be empty!")
    private String password;
}
