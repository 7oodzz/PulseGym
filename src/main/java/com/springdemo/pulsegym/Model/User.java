package com.springdemo.pulsegym.Model;


public abstract class User {

    private String username;
    private String password;
    private String SSN;

    public User(String username, String password, String SSN) {
        setUsername(username);
        setPassword(password);
        setSSN(SSN);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        validateField(username, "Username");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        validateField(password, "Password");
        this.password = password;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        validateField(SSN, "SSN");
        this.SSN = SSN;
    }

    private void validateField(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be null or empty.");
        }
    }
}



