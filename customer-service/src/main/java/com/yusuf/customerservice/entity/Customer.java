package com.yusuf.customerservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cs_customer")
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @SequenceGenerator(name = "seq_customer", allocationSize = 1)
    @GeneratedValue(generator = "seq_customer", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
