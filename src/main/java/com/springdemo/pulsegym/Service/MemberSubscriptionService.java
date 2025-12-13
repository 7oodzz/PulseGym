package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.MemberSubscription;
import com.springdemo.pulsegym.Model.SubscriptionBundle;
import com.springdemo.pulsegym.Repository.*;
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
    @Autowired
    MemberSessionRepository sessionRepository;

    public Object addSubscriptionToMember(int subId, int memberId) {
        
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        SubscriptionBundle subscriptionBundle = subscriptionRepo.findById(subId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        if (member.getHasSubscription()) {
            throw(new RuntimeException("Member already has subscription!"));
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

        if(member.getHasSession()) {
            member.setHasSession(false);
            sessionRepository.removeByMember(member);
        }

        member.setHasSubscription(false);
        memberRepo.save(member);
        memberSubscriptionRepo.delete(memberSubscription);
        return "deletion successful";
    }


    public List<MemberSubscription> listMemberSubscriptions() {
        return memberSubscriptionRepo.findAll();
    }


    @Transactional
    @Scheduled(fixedRate = 60000) //cron = "0 0 0 * * ?" to make it update kol youm
    public void checkExpiryDates() {
        LocalDate today = LocalDate.now();
        List<MemberSubscription> allSubscriptions = memberSubscriptionRepo.findAll();

        for (MemberSubscription sub : allSubscriptions) {
            if (sub.getExpDate() != null && sub.getExpDate().isBefore(today)) {

                Member member = sub.getMember();

                if (member != null) {
                    member.setHasSubscription(false);
                    memberRepo.save(member);
                }

                memberSubscriptionRepo.delete(sub);
            }
        }
    }


}
