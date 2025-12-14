package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.SubscriptionBundle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionBundle, Integer> {

}
