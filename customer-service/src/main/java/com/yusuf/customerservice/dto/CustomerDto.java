package com.yusuf.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @NotEmpty(message = "Username can't be empty!")
    private String username;

    @NotEmpty(message = "Password can't be empty!")
    private String password;
}
