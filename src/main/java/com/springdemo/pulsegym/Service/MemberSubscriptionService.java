package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSubscription;
import com.springdemo.pulsegym.Model.SubscriptionBundle;
import com.springdemo.pulsegym.Repository.MemberRepository;
import com.springdemo.pulsegym.Repository.MemberSubscriptionRepo;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberSubscriptionService {
    @Autowired
    MemberSubscriptionRepo memberSubscriptionRepo;
    @Autowired
    MemberRepository memberRepo;
    @Autowired
    SubscriptionRepository subscriptionRepo;

    public Object addSubscriptionToMember(int subId, int memberId) {
//        boolean isMember=memberRepo.findById(memberId)!= null;
//        boolean isSubscription= subscriptionRepo.findById(subId) !=null;
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        SubscriptionBundle subscriptionBundle = subscriptionRepo.findById(subId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        if (member.getHasSubscription()) {
            return "Member already has subscription";
        } else {
            MemberSubscription memberSubObj = new MemberSubscription(member, subscriptionBundle, LocalDate.now(),
                    LocalDate.now().plusMonths(subscriptionBundle.getDurationInMonth()));
            member.setHasSubscription(true);
            return memberSubscriptionRepo.save(memberSubObj);
        }

    }

    public Object removeSubFromUser(int memberSubId) {
        MemberSubscription memberSubscription = memberSubscriptionRepo.findById(memberSubId)
                .orElseThrow(() -> new RuntimeException("Member's subscription not found"));
        int memberId = memberSubscription.getMember().getId();
        int subId = memberSubscription.getBundle().getId();
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        SubscriptionBundle subscriptionBundle = subscriptionRepo.findById(subId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        if (!member.getHasSubscription()) {
            return "subscription already removed";
        } else {
            member.setHasSubscription(false);
            memberSubscriptionRepo.delete(memberSubscription);
            return "deletion succesful";
        }

    }

    public List<MemberSubscription> listMemberSubscriptions() {
        return memberSubscriptionRepo.findAll();
    }

}
