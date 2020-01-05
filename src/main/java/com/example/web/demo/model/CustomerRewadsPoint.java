package com.example.web.demo.model;

import javax.persistence.*;

@Entity
public class CustomerRewadsPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    private Long point;

    protected CustomerRewadsPoint() {
    }

    public CustomerRewadsPoint(Customer customer, Long point) {
        this.point = point;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getPoint() {
        return point;
    }
}
