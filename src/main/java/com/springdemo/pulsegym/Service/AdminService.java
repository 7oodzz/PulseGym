package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.Model.Admin;
import com.springdemo.pulsegym.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    
    @Autowired 
    private AdminRepository adminRepo;
    @Autowired
    private PasswordEncoder encoder;

    public Admin registerAdmin(Admin admin) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        return adminRepo.save(admin);
    }

    public void deleteAdmin(Long id) {
        adminRepo.deleteById(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }
}
