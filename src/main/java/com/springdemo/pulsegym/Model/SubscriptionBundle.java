package com.springdemo.pulsegym.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
public class SubscriptionBundle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Price must be positive")
    private double price;

    @NotEmpty(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotNull(message = "Length of subscription is required")
    @Min(value = 0, message = "Length of subscription must be greater than zero")
    private int durationInMonth;



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setDurationInMonth(int durationInMonth) {
        this.durationInMonth = durationInMonth ;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getDurationInMonth() {
        return  durationInMonth;
    }
}
