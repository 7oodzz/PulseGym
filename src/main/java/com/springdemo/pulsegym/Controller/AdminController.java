package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Model.Admin;
import com.springdemo.pulsegym.Service.AdminService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springdemo.pulsegym.DTO.AuthRequest;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/createAdmin")
    public Object createAdmin(@Valid @RequestBody AuthRequest admin,BindingResult bindingResult ) {
         if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error->error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        else
            return adminService.registerAdmin(admin);
    }

    @GetMapping("/listAdmins")
    public List<Admin> listAdmins(){ 
        return adminService.getAllAdmins();
    }

    @PostMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
    
}