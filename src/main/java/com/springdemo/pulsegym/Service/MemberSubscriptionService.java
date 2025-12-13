package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSubscription;
import com.springdemo.pulsegym.Model.SubscriptionBundle;
import com.springdemo.pulsegym.Repository.MemberRepository;
import com.springdemo.pulsegym.Repository.MemberSubscriptionRepo;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
            memberRepo.save(member);
            return memberSubscriptionRepo.save(memberSubObj);
        }

    }

    public String removeSubFromMember(int memberSubId) {

        MemberSubscription memberSubscription = memberSubscriptionRepo.findById(memberSubId)
                .orElseThrow(() -> new RuntimeException("Member's subscription not found"));

        Member member = memberSubscription.getMember();

        if (!member.getHasSubscription()) {
            return "subscription already removed";
        }

        member.setHasSubscription(false);
        memberRepo.save(member);
        memberSubscriptionRepo.delete(memberSubscription);
        return "deletion successful";
    }


    public List<MemberSubscription> listMemberSubscriptions() {
        return memberSubscriptionRepo.findAll();
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkExpiryDates() {

        List<MemberSubscription> subscriptions = memberSubscriptionRepo.findAll();
        LocalDate today = LocalDate.now();

        for(MemberSubscription sub : subscriptions) {
            if(sub.getExpDate().isBefore(today)) {
                Member member = sub.getMember();
                if (member != null) {
                    member.setHasSubscription(false);
                    memberRepo.save(member);
                }
            }
            memberSubscriptionRepo.delete(sub);
        }

    }


}
