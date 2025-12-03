package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSubscriptionRepo extends JpaRepository<MemberSubscription,Integer> {
}
