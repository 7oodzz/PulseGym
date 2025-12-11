package com.springdemo.pulsegym.Configuration;

import com.springdemo.pulsegym.Model.Admin;
import com.springdemo.pulsegym.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(adminRepo.count() == 0) {
            adminRepo.save(new Admin("admin", passwordEncoder.encode("admin123123")));
        }
    }

}
