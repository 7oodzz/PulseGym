package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.Session;
import com.springdemo.pulsegym.Model.Subscription;
import com.springdemo.pulsegym.Repository.MemberRepository;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private SubscriptionRepository subRepo;

    public Member addMember(Member member){
        return memberRepo.save(member);
    }
    public Member updateMember(Member newMember){
        Member member = memberRepo.findById(newMember.getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setId(newMember.getId());
        member.setSubscription(newMember.getSubscription());
        member.setName(newMember.getName());
        member.setPhoneNo(newMember.getPhoneNo());
        return memberRepo.save(member);
    }

//    public String addNewSubToMember(int memberId, int subId){
//        Member member= memberRepo.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//        Subscription sub=subRepo.findById(subId)
//                .orElseThrow(() -> new RuntimeException("Subscription bundle not found"));
//
//        if(member.getSubscription()==null || !member.getSubscription().status(subId)){
//            member.setSubscription(sub);
//            return" Subscription added succefully";
//        }
//        else if(member.getSubscription().status(subId)){
//            return "Member already has active subscription";
//        }
//        else
//           return "An Error has occured";
//    }
//    public String removeCurrentSubscription(int memberId) {
//        Member member = memberRepo.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        if (member.getSubscription() == null) {
//            return "member doesn't have a subscription";
//        } else {
//            Subscription oldSub = member.getSubscription();
//            member.setSubscription(null);
//            int oldSubId = oldSub.getId();
//            return "Subscription:" + oldSubId + "succesfully removed";
//        }
//    }
//
//    public String addNewSessionToMember(int memberId , int sessionId){
//        Member member= memberRepo.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//        Subscription sub=subRepo.findById(sessionId)
//                .orElseThrow(() -> new RuntimeException("Session bundle not found"));
//
//        if(member.getSubscription()==null || !member.getSubscription().sessionStatus){
//            member.setSubscription(sub);
//            return" Session added succefully";
//        }
//        else if(member.getSubscription().sessionStatus){
//            return "Member already has active subscription";
//        }
//        else
//            return "An Error has occured";
//    }
//    public String removeCurrentSubscription(int memberId) {
//        Member member = memberRepo.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        if (member.getSession() == null) {
//            return "member doesn't have a Session";
//        } else {
//            Session oldSession = member.getSession();
//            member.setSession(null);
//            int oldSessionId = oldSession.getId();
//            return "Session:" + oldSessionId + "succesfully removed";
//        }
//    }

}


