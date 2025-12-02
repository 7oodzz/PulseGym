package com.springdemo.pulsegym.Util;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.springdemo.pulsegym.Model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private String key = "my-secret";

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("type", user.getClass().getSimpleName()) //type of user
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractType(String token) {
        return (String) Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get("type");
    }

    public boolean validate(String token) { 
        try {
            extractUsername(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void validateUserType(String token, String requiredType) {
    if (token == null || !validate(token)) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    }

    String type = extractType(token);
    if (!requiredType.equals(type)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
    }
}
}
