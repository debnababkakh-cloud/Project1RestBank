package com.example.project1.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Key;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, Key key) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // отключаем CSRF для REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/register").permitAll() // открытые эндпоинты
                        .anyRequest().authenticated() // всё остальное требует токен
                )
                // добавляем наш JWT фильтр перед стандартным фильтром аутентификации
                .addFilterBefore(new JwtAuthFilter(key), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public Key jwtSigningKey() {
        String secret = "мой-длинный-секрет-для-hs256-который-больше-32-символов";
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}