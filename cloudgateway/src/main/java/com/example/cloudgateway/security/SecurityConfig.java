package com.example.cloudgateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        return http
                .authorizeExchange()
                .pathMatchers("/api/security/oauth/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/usuarios/usuarios/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/productos/producto/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/usuarios/usuarios").hasAuthority("ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/api/usuarios/usuarios/**").hasAuthority("ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }
}
