package com.springdemo.pulsegym.Model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String memberName;
    private String sessionDate;
    private double durationHours;
    private Plan plan;
    private int sessionsLeft;
    private LocalDate expiryDate;

    public Session() {
    }

    public Session(int id, String memberName, String sessionDate, double durationHours, Plan plan, int sessionsLeft, LocalDate expiryDate) {
        this.id = id;
        this.memberName = memberName;
        this.sessionDate = sessionDate;
        this.durationHours = durationHours;
        this.sessionsLeft = sessionsLeft;
        this.expiryDate = expiryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setClientName(String memberName) {
        this.memberName = memberName;
    }

    public String getmemberName() {
        return memberName;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setDurationHours(double durationHours) {
        this.durationHours = durationHours;
    }

    public double getDurationHours() {
        return durationHours;
    }

    public int getSessionsLeft() {
        return sessionsLeft;
    }

    public void setSessionsLeft(int sessionsLeft) {
        this.sessionsLeft = sessionsLeft;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "PrivateSession{" +
                "id=" + id +
                ", memberName='" + memberName + '\'' +
                ", sessionDate='" + sessionDate + '\'' +
                ", durationHours=" + durationHours +
                '}';
    }
}
