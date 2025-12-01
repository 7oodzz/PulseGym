package com.springdemo.pulsegym.Controller;

import com.springdemo.pulsegym.Util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private JwtUtil jwt;

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard(@RequestHeader("Authorization") String token) {
        String type = jwt.extractType(token);
        if (!"Admin".equals(type)) {
            return ResponseEntity.status(403).body("Access denied");
        }
        return ResponseEntity.ok("Welcome Admin!");
    }
}