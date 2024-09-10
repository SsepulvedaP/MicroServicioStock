package com.MicroService.MicroServiceStock.infrastructure.configuration.security;

import com.MicroService.MicroServiceStock.application.dto.request.AuthRequest;
import com.MicroService.MicroServiceStock.application.dto.response.AuthResponse;
import com.MicroService.MicroServiceStock.infrastructure.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        AuthRequest authRequest = AuthRequest.builder()
                .token(token)
                .build();
        AuthResponse authResponse = new AuthResponse(authRequest);

        if (!authResponse.isAuthorized()) throw new NotAuthorizedException(authResponse.getEmail());

        Collection<? extends GrantedAuthority> authorities =
                Set.of(new SimpleGrantedAuthority(authResponse.getRole()));

        return new User(authResponse.getEmail(), token, authorities);
    }



}
