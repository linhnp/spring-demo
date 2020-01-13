package com.example.web.demo.multipledb.model.web;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;

    private double price;
}
