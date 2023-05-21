package com.example.elm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "\"customer\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter

public class Customer {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "customer_primary_sequence",
            sequenceName = "customer_primary_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_primary_sequence"
    )
    private Integer id;

    private String nationalId;

    private String name;

    private LocalDate dob;

    private String email;

    private String mobile;



    public Customer()
    {

    }



}
