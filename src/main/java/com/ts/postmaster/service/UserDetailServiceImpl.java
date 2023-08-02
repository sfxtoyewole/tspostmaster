package com.ts.postmaster.service;

import com.ts.postmaster.dao.repository.IPMUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * @author toyewole
 */
@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final IPMUserRepository ipmUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            var user = ipmUserRepository.findPMUserByEmailOrUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("No User Found "+ username));


        return com.ts.postmaster.dto.UserDetails.builder().
                username(username)
                .password(user.getPassword())
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .authorities(getAuthorities("ROLE_ADMIN"))
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleUser) {
          return Collections.singletonList(new SimpleGrantedAuthority(roleUser));
    }
}
