package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Factory.UserFactory;
import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private UserFactory userFactory;

//    public Member addMember(Member member){
//        return memberRepo.save(member);
//    }

    public Member addMember(Member member) {
        Member newMember = userFactory.createMember(member);
        return memberRepo.save(newMember);
    }

    public Member updateMember(Member newMember){
        Member member = memberRepo.findById(newMember.getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setId(newMember.getId());
        member.setHasSubscription(newMember.getHasSubscription());
        member.setHasSession(newMember.getHasSession());
        member.setName(newMember.getName());
        member.setPhoneNo(newMember.getPhoneNo());
        return memberRepo.save(member);
    }

    public boolean isSubscribed(Member member){
        member = memberRepo.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return member.getHasSubscription();
    }
    public boolean hasSession(Member member){
        return member.getHasSession();
    }

    public List<Member> listMembers(){
        return memberRepo.findAll();
    }

}


