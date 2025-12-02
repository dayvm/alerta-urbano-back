package com.example.Alert_City.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String email) {
        return Jwts.builder()
                   .setSubject(email)
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                   .signWith(KEY, SignatureAlgorithm.HS256)
                   .compact();
    }

    public static String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build()
                   .parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        }
        catch(JwtException e) {
            return false;
        }
    }
}