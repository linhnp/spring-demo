package com.example.web.demo.repository;

import com.example.web.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserIp (String userIp);

    boolean existsByUserIp(String userIp);
}
