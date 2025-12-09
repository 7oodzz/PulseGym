package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSession;
import com.springdemo.pulsegym.Model.SessionBundle;
import com.springdemo.pulsegym.Repository.MemberRepository;
import com.springdemo.pulsegym.Repository.MemberSessionRepository;
import com.springdemo.pulsegym.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberSessionService {

    @Autowired
    private MemberSessionRepository memberSessionRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private SessionRepository sessionBundleRepo;

    public Object addSessionToMember(int sessionBundleId, int memberId) {

        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        SessionBundle sessionBundle = sessionBundleRepo.findById(sessionBundleId)
                .orElseThrow(() -> new RuntimeException("Session bundle not found"));

        if (member.getHasSession()) {
            return "Member already has a session bundle assigned";
        }

        MemberSession ms = new MemberSession();
        ms.setSessionBundleId(sessionBundle.getId());
        ms.setMemberId(member.getId());
        ms.setSessionsLeft(sessionBundle.getSessionsLeft());
        member.setHasSession(true);
        return memberSessionRepo.save(ms);
    }

    public Object removeSessionFromMember(int memberSessionId) {

        MemberSession memberSession = memberSessionRepo.findById(memberSessionId)
                .orElseThrow(() -> new RuntimeException("MemberSession record not found"));

        Member member = memberRepo.findById(memberSession.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!member.getHasSession()) {
            return "Session bundle already removed";
        }

        member.setHasSession(false);
        memberSessionRepo.delete(memberSession);
        return "Session bundle removed";
    }

    public List<MemberSession> listMemberSessions() {
        return memberSessionRepo.findAll();
    }
}
