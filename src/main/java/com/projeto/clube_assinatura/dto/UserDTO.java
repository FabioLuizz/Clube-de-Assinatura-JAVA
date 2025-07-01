package com.projeto.clube_assinatura.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Campo nome é obrigatório")
    private String Name;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "Campo email é obrigatório")
    private String Email;

    @NotBlank(message = "Campo senha é obrigatório")
    private String Password;

    private String Role;

}
