package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Attendance;
import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSession;
import com.springdemo.pulsegym.Repository.AttendanceRepository;
import com.springdemo.pulsegym.Repository.MemberRepository;
import com.springdemo.pulsegym.Repository.MemberSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberSessionRepository memberSessionRepository;

    public String checkInMember(int memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found!"));

        if (!member.getHasSubscription()) {
            return "Member not subscribed!";
        }

        if (member.getHasSession()) {
            // Updated logic based on user input
            Optional<MemberSession> sessionBox = memberSessionRepository.findByMemberAndSessionsLeftGreaterThan(member, 0);

            if (sessionBox.isEmpty()) {
                member.setHasSession(false);
                memberRepository.save(member);
            } else {
                MemberSession session = sessionBox.get();
                session.setSessionsLeft(session.getSessionsLeft() - 1);
                memberSessionRepository.save(session);

                if (session.getSessionsLeft() == 0) {
                    member.setHasSession(false);
                    memberRepository.save(member);
                    memberSessionRepository.deleteById(session.getSessionId());
                }
            }
        }

        Attendance attendance = new Attendance(member);
        attendanceRepository.save(attendance);

        return "Check-in successful for member: " + member.getName();
    }
}