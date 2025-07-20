package com.projeto.clube_assinatura.config;

import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.repository.UserRepository;
import com.projeto.clube_assinatura.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class VerifyToken extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public VerifyToken(TokenService tokenService, UserRepository userRepository)
    {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String email = tokenService.getSubject(token);

                User user  = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                return;
            }
        }

        filterChain.doFilter(request, response);

    }



}
