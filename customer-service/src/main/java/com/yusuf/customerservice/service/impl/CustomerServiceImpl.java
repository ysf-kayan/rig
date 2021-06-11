package com.yusuf.customerservice.service.impl;

import com.yusuf.customerservice.dto.CredentialsDto;
import com.yusuf.customerservice.dto.CustomerDto;
import com.yusuf.customerservice.dto.CustomerTokenDto;
import com.yusuf.customerservice.entity.Customer;
import com.yusuf.customerservice.repo.CustomerRepository;
import com.yusuf.customerservice.service.CustomerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.CharBuffer;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${cs.secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));

        customerRepository.save(customer);

        return customerDto;
    }

    @Override
    public CustomerTokenDto login(CredentialsDto credentialsDto) {
        Customer customer = customerRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Username or password incorrect"));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), customer.getPassword())) {
            CustomerTokenDto customerDto = new CustomerTokenDto();
            customerDto.setUsername(customer.getUsername());
            customerDto.setToken(createToken(customer));
            return customerDto;
        }

        throw new RuntimeException("Username or password incorrect");
    }

    @Override
    public CustomerTokenDto validateToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);

        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOptional.get();

        return new CustomerTokenDto(customer.getUsername(), createToken(customer));
    }

    private String createToken(Customer customer) {
        Claims claims = Jwts.claims().setSubject(customer.getUsername());

        Date now = new Date();
        Date validity = new Date(now.getTime() + 60 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
