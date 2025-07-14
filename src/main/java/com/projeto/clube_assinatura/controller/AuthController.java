package com.projeto.clube_assinatura.controller;

import com.projeto.clube_assinatura.dto.LoginDTO;
import com.projeto.clube_assinatura.dto.UserDTO;
import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.service.TokenService;
import com.projeto.clube_assinatura.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO userDTO) {
        User userSave = userService.registerUser(userDTO);

        return ResponseEntity.ok(userSave);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {

        try{
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(), loginDTO.getPassword()
            );

            Authentication auth = authenticationManager.authenticate(login);

            String token = tokenService.generatedToken(loginDTO.getEmail());

            return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("{\"error\": \"Credenciais inválidas\"}");
        }


    }




}
