package com.example.elm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "\"service\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Service {
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "service_primary_sequence",
            sequenceName = "service_primary_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_primary_sequence"
    )
    private Integer id;


    private String title;


    private String description;


    private Integer duration;


    private Double cost;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)

    private Customer customer;

    public Service(){

    }

}
