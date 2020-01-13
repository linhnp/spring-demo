package com.example.web.demo.multipledb.repository.web;

import com.example.web.demo.multipledb.model.web.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
