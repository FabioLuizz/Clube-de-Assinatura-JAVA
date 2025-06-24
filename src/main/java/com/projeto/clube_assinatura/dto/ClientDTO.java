package com.projeto.clube_assinatura.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {

    @NotBlank(message = "Nome é obrigatório.")
    private String Name;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "Email é obrigatório.")
    private String Email;

    @NotBlank(message = "Cpf é obrigatório.")
    private String Cpf;

    @NotBlank(message = "Telefone é obrigatório.")
    private String Tel;
}
