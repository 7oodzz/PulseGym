package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Admin;
import com.springdemo.pulsegym.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional <User> findByUsername(String username);
}
