package com.springdemo.pulsegym.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;
import com.springdemo.pulsegym.Service.UserService;
import com.springdemo.pulsegym.Model.User;
import jakarta.validation.Valid;
import com.springdemo.pulsegym.DTO.LoginRequest;


@RestController
public class LoginController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(error->error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            User user = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(user);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid credentials.");
        }
    }

}
