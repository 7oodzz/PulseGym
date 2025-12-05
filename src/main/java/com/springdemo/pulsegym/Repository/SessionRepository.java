package com.springdemo.pulsegym.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springdemo.pulsegym.Model.SessionBundle;
import com.springdemo.pulsegym.Model.SubscriptionBundle;

public interface SessionRepository extends JpaRepository<SessionBundle, Integer> {
}

