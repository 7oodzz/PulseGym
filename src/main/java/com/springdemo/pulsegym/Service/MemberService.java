package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepo;

    public Member addMember(Member member){
        return memberRepo.save(member);
    }
    public Member updateMember(Member member){
        return memberRepo.save(member);
    }


}
