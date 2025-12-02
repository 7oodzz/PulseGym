package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
