package com.example.web.demo.multipledb.model.web;

import com.example.web.demo.multipledb.model.web.Customer;

import javax.persistence.*;

@Entity
@Table(name = "customer_rewards_point")
public class customerRewardsPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Customer customer;

    private Long point;

    protected customerRewardsPoint() {
    }

    public customerRewardsPoint(Customer customer, Long point) {
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
