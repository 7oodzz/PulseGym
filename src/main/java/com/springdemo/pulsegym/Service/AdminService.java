package com.springdemo.pulsegym.Service;

import com.springdemo.pulsegym.DTO.AuthRequest;
import com.springdemo.pulsegym.Factory.UserFactory;
import com.springdemo.pulsegym.Model.Admin;
import com.springdemo.pulsegym.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;
    @Autowired
    private UserFactory userFactory;

    public Admin registerAdmin(AuthRequest adminReq) {
        if (adminRepo.findByUsername(adminReq.getUsername()).isPresent()) {
            throw new IllegalStateException("admin already exists");
        }

        Admin admin = userFactory.createAdmin(adminReq);
        return adminRepo.save(admin);
    }

    public void deleteAdmin(int id) {
        adminRepo.deleteById(id);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }
}
