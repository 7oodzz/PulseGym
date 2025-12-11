package com.springdemo.pulsegym.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admins")
public class Admin extends User{

    public Admin () {}

    public Admin (String username, String password) {
        this.username = username;
        this.password = password;
    }
    
}
