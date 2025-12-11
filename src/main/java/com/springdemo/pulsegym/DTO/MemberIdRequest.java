package com.springdemo.pulsegym.DTO;

public class MemberIdRequest {
    private int memberId;
    private int itemId; // can be subId or sessionId

    // Getters and Setters
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
}

