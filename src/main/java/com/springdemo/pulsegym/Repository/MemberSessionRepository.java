package com.springdemo.pulsegym.Repository;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSessionRepository extends JpaRepository<MemberSession, Integer> {
    Optional<MemberSession> findByMemberAndSessionsLeftGreaterThan(Member member, int zero);

    @Transactional
    void removeByMember(Member member);
}
