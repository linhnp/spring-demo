package com.example.web.demo.multipledb.repository.web1;

import com.example.web.demo.multipledb.model.web1.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
