package com.MicroService.MicroServiceStock.infrastructure.configuration.security;


import com.MicroService.MicroServiceStock.infrastructure.configuration.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.MicroService.MicroServiceStock.utils.Constants.ADMIN_ROLE;
import static com.MicroService.MicroServiceStock.utils.Constants.AUX_BODEGA_ROLE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class ConfigFilter {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/article/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PATCH,"/article/updateStock").hasAuthority(AUX_BODEGA_ROLE)
                        .requestMatchers(HttpMethod.POST,"/category/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST,"/brand/**").hasRole(ADMIN_ROLE)
                        .requestMatchers("/article/**").permitAll()
                        .requestMatchers("/category/**").permitAll()
                        .requestMatchers("/brand/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
