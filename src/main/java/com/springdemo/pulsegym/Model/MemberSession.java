package com.springdemo.pulsegym.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MemberSession {
    @Id
    @GeneratedValue
    int id;
    int subscriptionBundleId;
    int sessionBundleId;
    int memberId;
    int sessionsLeft;

    public int getSessionsLeft() {
        return sessionsLeft;
    }
    public void setSessionsLeft(int sessionsLeft) {
        this.sessionsLeft = sessionsLeft;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getSubscriptionBundleId() {
        return subscriptionBundleId;
    }
    public void setSubscriptionBundleId(int subscriptionBundleId) {
        this.subscriptionBundleId = subscriptionBundleId;
    }
    public int getSessionBundleId() {
        return sessionBundleId;
    }
    public void setSessionBundleId(int sessionBundleId) {
        this.sessionBundleId = sessionBundleId;
    }
    public int getMemberId() {
        return memberId;
    }
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }



}
