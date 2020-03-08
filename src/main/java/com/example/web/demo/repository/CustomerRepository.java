package com.example.web.demo.repository;

import com.example.web.demo.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);

//    Customer findById(long id);

    Page<Customer> findByLastName(String name, Pageable pageable);

    @Transactional
    @Modifying(flushAutomatically = true)
    @Query("update Customer c set c.lastName = :last_name")
    void updateAll(@Param("last_name")String lastname);
}
