package com.example.web.demo.repository;

import com.example.web.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}
