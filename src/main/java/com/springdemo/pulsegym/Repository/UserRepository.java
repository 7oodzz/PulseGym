package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long>{
    User findByUsername(String username);
}
