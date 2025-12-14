package com.springdemo.pulsegym.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.springdemo.pulsegym.Model.User;
import com.springdemo.pulsegym.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder encoder;


    public User login(String username, String rawPassword) {

        User user = userRepo.findByUsername(username);

        if (user == null || !encoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}

