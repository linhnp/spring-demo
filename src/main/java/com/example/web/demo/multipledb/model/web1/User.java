package com.example.web.demo.multipledb.model.web1;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private int age;

}
