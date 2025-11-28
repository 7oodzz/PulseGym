package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    
}
