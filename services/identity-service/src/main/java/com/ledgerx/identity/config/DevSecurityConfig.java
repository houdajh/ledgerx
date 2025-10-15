package com.ledgerx.identity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("dev") // active only when profile=dev
public class DevSecurityConfig {

    @Bean
    SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/public/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()); // Basic Auth in dev
        return http.build();
    }

    @Bean
    InMemoryUserDetailsManager users(PasswordEncoder pe) {
        UserDetails reader = User.withUsername("user")
                .password(pe.encode("password"))
                .roles("read")       // -> ROLE_read
                .build();
        UserDetails writer = User.withUsername("admin")
                .password(pe.encode("password"))
                .roles("read","write") // -> ROLE_write
                .build();
        return new InMemoryUserDetailsManager(reader, writer);
    }

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
