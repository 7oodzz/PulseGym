package com.springdemo.pulsegym.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Username cannot be empty")
    private final String username;

    @NotBlank(message = "Password cannot be empty")
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
