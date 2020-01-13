package com.example.web.demo.multipledb.model.web;

import com.example.web.demo.model.Auditable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    protected Customer(){}

    public Customer(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<customerRewardsPoint> points = new HashSet<>();

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
