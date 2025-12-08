package com.springdemo.pulsegym.Model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class SessionBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Description is required")
    private String description;
    private double durationHours; 
    private Plan plan;
    private int NumberOfSessions;       
    private String sessionDate;    

    public SessionBundle() {
    }

    
    public SessionBundle(int id,int NumberOfSessions, String sessionDate, double durationHours, Plan plan ,String description) {
        this.id = id;
        this.NumberOfSessions = NumberOfSessions;
        this.sessionDate = sessionDate;
        this.durationHours = durationHours;
        this.plan = plan;
        this.description = description;
    }  
    public void setId(int id) 
    { 
        this.id = id;
    }

    public int getId() {

    public int getId() {
        return id;
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
    public double getDurationHours() {
        return durationHours;
    }

    public int getNumberOfSessions() {
        return NumberOfSessions;
    }

    public void setNumberOfSessions(int NumberOfSessions) {
        this.NumberOfSessions = NumberOfSessions;
    }
     public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
 
    public Plan getPlan() { return plan; }
    
    public void setPlan(Plan plan) { this.plan = plan; }
}