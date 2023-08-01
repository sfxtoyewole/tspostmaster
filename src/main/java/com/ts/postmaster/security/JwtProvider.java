package com.ts.postmaster.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Key;

/**
 * @author toyewole
 */
@Service
public class JwtProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public String generateToken (Authentication authentication){
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();

    }


    public boolean validateToken (String jwt){
        Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token )
                .getBody();


        return claims.getSubject();
    }
}
