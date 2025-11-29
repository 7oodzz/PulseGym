package com.springdemo.pulsegym.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springdemo.pulsegym.Model.User;

import com.springdemo.pulsegym.Repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public User login(String username, String password) {

        Optional<User> userOptional = userRepo.findByUsername(username); 

        if (userOptional.isPresent()) { 
            User user=userOptional.get();
            if (password.equals(user.getPassword())) {
                return user; 
            } else {
                throw new IllegalArgumentException("Invalid password.");
            }
        } else {
            throw new IllegalArgumentException("User not found with username: " + username);
        }

    }


}
