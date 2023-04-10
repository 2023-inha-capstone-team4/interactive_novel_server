package com.capstone.interactive_novel.common.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenComponents {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    public static String removeTokenHeader(String token, String header) {
        if(token.startsWith(header)) {
            token = token.substring(header.length());
        }
        return token;
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
