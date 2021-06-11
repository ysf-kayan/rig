package com.yusuf.customerservice.service;

import com.yusuf.customerservice.dto.CredentialsDto;
import com.yusuf.customerservice.dto.CustomerDto;
import com.yusuf.customerservice.dto.CustomerTokenDto;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    CustomerTokenDto login(CredentialsDto credentialsDto);

    CustomerTokenDto validateToken(String token);
}
