package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
