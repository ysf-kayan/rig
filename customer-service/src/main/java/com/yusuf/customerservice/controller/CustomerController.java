package com.yusuf.customerservice.controller;

import com.yusuf.customerservice.dto.CredentialsDto;
import com.yusuf.customerservice.dto.CustomerDto;
import com.yusuf.customerservice.dto.CustomerTokenDto;
import com.yusuf.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.save(customerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CustomerTokenDto> signIn(@RequestBody CredentialsDto credentialsDto) {
        return ResponseEntity.ok(customerService.login(credentialsDto));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<CustomerTokenDto> signIn(@RequestParam String token) {
        return ResponseEntity.ok(customerService.validateToken(token));
    }
}
