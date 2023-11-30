package com.company.explorecalifornia.security;

import com.company.explorecalifornia.security.jwt.JWTAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The {@code WebSecurityConfig} class contains the Spring security configuration.
 *
 */
@Configuration
public class SecurityConfig {

    private final AccessDeniedEntryPoint accessDeniedEntryPoint;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(AccessDeniedEntryPoint accessDeniedEntryPoint, AuthenticationEntryPoint authenticationEntryPoint,
                          JWTAuthFilter jwtAuthFilter) {

        this.accessDeniedEntryPoint = accessDeniedEntryPoint;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.POST, "/registration", "/authentication").permitAll()
                        .requestMatchers(HttpMethod.GET, "/tour-packages/**", "/tours/**",
                                                        "/tour-ratings/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/tour-ratings/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/tour-ratings/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/tour-ratings/**").authenticated()
                        .anyRequest().hasRole("ADMIN")
                        );
        httpSecurity.sessionManagement(sessionConfigurer ->
                sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.exceptionHandling(exceptionHandler ->
                exceptionHandler.accessDeniedHandler(accessDeniedEntryPoint)
                        .authenticationEntryPoint(authenticationEntryPoint));

        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}