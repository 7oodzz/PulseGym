package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Member;
import com.springdemo.pulsegym.Model.Subscription;
import com.springdemo.pulsegym.Repository.MemberRepository;
import com.springdemo.pulsegym.Repository.SubscriptionRepository;

public class ReceptionistService {

    @Autowired
    private final MemberRepository memeberRepo;

    @Autowired
    private final SubscriptionRepository subscriptionRepo;

    public ReceptionistService(MemberRepository memeberRepo,SubscriptionRepository subscriptionRepo){
        this.memeberRepo=memeberRepo;
        this.subscriptionRepo=subscriptionRepo;
    }

    public Member addMember(String name, String phoneNo, String ssn){
        Member m = new Member();
        m.setName(name);
        m.setPhoneNo(phoneNo);
        m.setSsn(ssn);
        return memeberRepo.save(m);
    }

    public Member updateMember(String id , Member updated){
        Member original =memeberRepo.findById(id)
                       .orElseThrow(() ->new RuntimeException("Member not found"));
        original.setName(updated.getName());
        original.setPhoneNo(updated.getPhoneNo());
        original.setSsn(updated.getSsn());
        return memeberRepo.save(original);
    }

    public Subscription addSubscriptionToMember(String memberId,Subscription subscription){
        Member mem =memeberRepo.findById(memberId)
                .orElseThrow(() ->new RuntimeException("Member not found"));
        subscription.setMember(mem);
        return subscriptionRepo.save(subscription);
    }

    public void cancelSubscription(String subscriptionId){
        subscriptionRepo.deleteById(subscriptionId);
    }


}
