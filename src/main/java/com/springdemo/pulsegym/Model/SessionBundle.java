package com.springdemo.pulsegym.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;



@Entity
public class SessionBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Title is required")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters")
    private String title;

    @NotEmpty(message = "Long term goal is required")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters")
    private String longTermGaol;

    @Min(1)
    private int sessionsAmount;

    public SessionBundle() {}

    public SessionBundle(String title, String longTermGaol, int sessionsAmount) {
        this.title = title;
        this.longTermGaol = longTermGaol;
        this.sessionsAmount = sessionsAmount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getLongTermGaol() {
        return longTermGaol;
    }

    public void setLongTermGaol(String longTermGaol) {
        this.longTermGaol = longTermGaol;
    }

    public int getSessionsAmount() {
        return sessionsAmount;
    }

    public void setSessionsAmount(int sessionsAmount) {
        this.sessionsAmount = sessionsAmount;
    }



}
