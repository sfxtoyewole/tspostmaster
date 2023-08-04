package com.ts.postmaster.security;

import com.ts.postmaster.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * @author toyewole
 */
@Slf4j
@Service
public class JwtProvider {


    private Key key;
    @PostConstruct
    protected void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(Authentication authentication) {

        User principal = (User) authentication.getPrincipal();

        return getJwtToken(principal.getUsername());


    }

    public String getJwtToken(String principal) {

        return Jwts.builder()
                .setSubject(principal)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }


    public boolean validateToken(String jwt) {

        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt);

            return true;

        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException JWT Token Error ", e);
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT Token Validation Error ", e);
            return false;
        }catch (Exception e){
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();


        return claims.getSubject();
    }
}
