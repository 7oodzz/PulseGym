package com.springdemo.pulsegym.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="members")
public class Member {
    @Id
    @GeneratedValue
    private int id;
    @Pattern(
            regexp = "^01[0-2,5]{1}[0-9]{8}$",
            message = "Invalid Egyptian phone number format"
    )
    private String phoneNo;
    private boolean hasSubscription;
    private boolean hasSession;
    @NotEmpty(message = "Member name can't be empty")
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }
     public void setHasSession(boolean hasSession){
        this.hasSession=hasSession;
     }
     public boolean getHasSession(){
        return this.hasSession;
     }
     public boolean getHasSubscription(){
        return this.hasSubscription;
     }
    public void setHasSubscription(boolean hasSubscription){
        this.hasSubscription=hasSubscription;
    }







}
