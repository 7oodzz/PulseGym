package com.springdemo.pulsegym.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class MemberSubscription {
    @Id
    @GeneratedValue
    int id;
    LocalDate expDate;
    LocalDate startDate;
    int subscriptionBundleId;
    int sessionBundleId;
    int memberId;


    public void setExpDate(LocalDate expDate){
        this.expDate=expDate;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate=startDate;
    }
    public LocalDate getExpDate(){
        return this.expDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setSubscriptionBundleId(int subscriptionBundleId){
        this.subscriptionBundleId=subscriptionBundleId;
    }
    public void setSessionBundleId(int sessionBundleId){
        this.sessionBundleId=sessionBundleId;
    }

    public int getSubscriptionBundleId() {
        return subscriptionBundleId;
    }

    public int getSessionBundleId() {
        return sessionBundleId;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
