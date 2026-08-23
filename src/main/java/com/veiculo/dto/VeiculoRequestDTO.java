package com.veiculo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record VeiculoRequestDTO(

        @NotBlank(message = "Placa é obrigatória")
        String placa,

        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotNull(message = "Ano é obrigatório")
        @Positive(message = "Ano deve ser um valor positivo")
        Integer ano,

        @NotNull(message = "Marca é obrigatória")
        Long marcaId,

        @PositiveOrZero(message = "Quilometragem não pode ser negativa")
        Double quilometragemAtual
) {
}
