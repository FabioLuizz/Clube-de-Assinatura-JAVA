package com.projeto.clube_assinatura.controller;

import com.projeto.clube_assinatura.dto.UserDTO;
import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO userDTO) {
        User userSave = userService.registerUser(userDTO);

        return ResponseEntity.ok(userSave);
    }


}
