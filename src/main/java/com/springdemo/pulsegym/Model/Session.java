package com.springdemo.pulsegym.Model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
    private String trainerName;
    private String memberName;
    
    private String sessionDate; 

    private double durationHours; 
    private Plan plan;
    public Session() { }

    public Session(int id, String trainerName, String memberName, String sessionDate, double durationHours,Plan plan) {
        this.id = id;
        this.trainerName = trainerName;
        this.memberName = memberName;
        this.sessionDate = sessionDate;
        this.durationHours = durationHours;
    }

    public void setId(int id) 
    { 
        this.id = id;
    }
    public int getId() 
    { 
        return id;
    }
    public void setTrainerName(String trainerName) 
    {
         this.trainerName = trainerName; 
}
    public String getTrainerName()
    { 
        return trainerName;
    }

    public void setClientName(String memberName)
    { 
        this.memberName = memberName; 
    }
    public String getmemberName() 
    {
        return memberName; 
    }
    public void setSessionDate(String sessionDate)
    { 
        this.sessionDate = sessionDate;
    }
    public String getSessionDate() 
    { 
        return sessionDate; 
    }
    public void setDurationHours(double durationHours)
    { 
        this.durationHours = durationHours; 
    }

    public double getDurationHours() 
    { 
        return durationHours; 
    }
 
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    
    @Override
    public String toString() {
        return "PrivateSession{" +
                "id=" + id +
                ", trainerName='" + trainerName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", sessionDate='" + sessionDate + '\'' +
                ", durationHours=" + durationHours +
                '}';
    }
}
