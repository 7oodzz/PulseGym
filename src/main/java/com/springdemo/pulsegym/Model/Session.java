package com.springdemo.pulsegym.Model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// description 

public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double durationHours; 
    private Plan plan;
    private int NumberOfSessions;       
    private LocalDate expiryDate;    

    public Session() { }

    public Session(int id, double durationHours,Plan plan ,int NumberOfSessions, LocalDate expiryDate) {
        this.id = id;
        this.durationHours = durationHours;
        this.NumberOfSessions = NumberOfSessions;
    }

    public void setId(int id) 
    { 
        this.id = id;
    }
    public int getId() 
    { 
        return id;
    }
    public void setDurationHours(double durationHours)
    { 
        this.durationHours = durationHours; 
    }

    public double getDurationHours() 
    { 
        return durationHours; 
    }
  public int getNumberOfSessions() {
        return NumberOfSessions;
    }

    public void setSessionsLeft(int NumberOfSessions) {
        this.NumberOfSessions = NumberOfSessions;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    
    
    @Override
    public String toString() {
        return "PrivateSession{" +
                "id=" + id +
                ", durationHours=" + durationHours +
                '}';
    }
}
