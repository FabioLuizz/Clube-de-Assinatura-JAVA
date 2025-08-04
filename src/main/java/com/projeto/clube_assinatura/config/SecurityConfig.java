package com.projeto.clube_assinatura.config;

import com.projeto.clube_assinatura.repository.UserRepository;
import com.projeto.clube_assinatura.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenService tokenService;
    private UserRepository userRepository;

    public SecurityConfig(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/plan").permitAll()
                        .requestMatchers(HttpMethod.GET, "/plan").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/plan/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/plan/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/sub").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/sub/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/sub/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/sub/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/payment/checkout/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new VerifyToken(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
