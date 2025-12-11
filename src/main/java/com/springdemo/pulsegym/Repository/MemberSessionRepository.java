package com.springdemo.pulsegym.Repository;
import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSessionRepository extends JpaRepository<MemberSession, Integer> {
    Optional<MemberSession> findByMemberAndIsActiveTrue(Member member);

    void deleteByInt(int sessionId);
}
