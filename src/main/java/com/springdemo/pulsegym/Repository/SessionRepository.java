package com.springdemo.pulsegym.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springdemo.pulsegym.Model.Session;
import com.springdemo.pulsegym.Model.Subscription;

public interface SessionRepository extends JpaRepository<Session, Integer> {
}

