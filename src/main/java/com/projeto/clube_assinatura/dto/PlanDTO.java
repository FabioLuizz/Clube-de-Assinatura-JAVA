package com.projeto.clube_assinatura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {

    @NotBlank(message = "Nome do plano é obrigatório!")
    private String name;

    @NotBlank(message = "Descrição é um campo obrigatório!")
    private String description;

    @NotNull(message = "Valor do plano é obrigatório!")
    private BigDecimal price;

    @NotNull(message = "Duração do plano é obrigatório!")
    private Integer duration;

}
