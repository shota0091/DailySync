package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRFを一時無効化（POST確認のため）
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 全リクエストを許可
            )
            .httpBasic(Customizer.withDefaults()); // Basic認証も一応有効

        return http.build();
    }
}
