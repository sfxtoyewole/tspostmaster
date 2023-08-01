package com.ts.postmaster.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author toyewole
 */
@Builder
@Data
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    Collection<? extends GrantedAuthority> authorities;

}
