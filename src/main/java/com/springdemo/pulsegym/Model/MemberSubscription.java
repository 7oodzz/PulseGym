package com.springdemo.pulsegym.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class MemberSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private LocalDate expDate;

    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "bundle_id", nullable = false)
    private SubscriptionBundle bundle;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public int getId() {
        return id;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpDate() {
        return this.expDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public SubscriptionBundle getBundle() { return bundle; }
    public void setBundle(SubscriptionBundle bundle) { this.bundle = bundle; }

    public MemberSubscription(Member member, SubscriptionBundle bundle, LocalDate startDate, LocalDate expDate) {
        this.member = member;
        this.bundle = bundle;
        this.startDate = startDate;
        this.expDate = expDate;
    }

    public MemberSubscription() {

    }
}
