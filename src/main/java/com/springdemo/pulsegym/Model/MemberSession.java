package com.springdemo.pulsegym.Model;

import jakarta.persistence.*;

@Entity
public class MemberSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bundle_id", nullable = false)
    private SessionBundle bundle;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private int sessionsLeft;

    public MemberSession() {
    }

    public MemberSession(Member member, SessionBundle bundle) {
        this.member = member;
        this.bundle = bundle;
        this.sessionsLeft = bundle.getSessionsAmount();
    }

    public SessionBundle getBundle() {
        return bundle;
    }

    public void setBundle(SessionBundle bundle) {
        this.bundle = bundle;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getSessionsLeft() {
        return sessionsLeft;
    }

    public void setSessionsLeft(int sessionsLeft) {
        this.sessionsLeft = sessionsLeft;
    }

    public int getSessionId() {
        return id;
    }
}
