package com.springdemo.pulsegym.Util;

import org.springframework.stereotype.Component;
import com.springdemo.pulsegym.Model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import java.util.UUID;

@Component
public class JwtUtil {
    @Value("${jwt.key}")
    private String key;

    public String generateToken(User user) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
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

}
