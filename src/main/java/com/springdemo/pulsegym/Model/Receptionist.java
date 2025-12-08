package com.springdemo.pulsegym.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Receptionists")
public class Receptionist extends User {
    @NotEmpty
    private String name;
    @NotEmpty
    private String ssn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Override
    public String toString() {
        return "Receptionist{" +
                "name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }
}
