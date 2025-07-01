package com.projeto.clube_assinatura.service;

import com.projeto.clube_assinatura.dto.UserDTO;
import com.projeto.clube_assinatura.model.User;
import com.projeto.clube_assinatura.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(@Valid UserDTO userDTO) {

        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail inserido já está cadastrado.");
        }

        User user = new User();
        user.setName(user.getName());
        user.setEmail(user.getEmail());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setRole("USER");

        return userRepository.save(user);
    }

}
