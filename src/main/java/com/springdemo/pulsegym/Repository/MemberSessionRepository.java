package com.springdemo.pulsegym.Repository;
import com.springdemo.pulsegym.Model.MemberSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, Integer> {
    
}
