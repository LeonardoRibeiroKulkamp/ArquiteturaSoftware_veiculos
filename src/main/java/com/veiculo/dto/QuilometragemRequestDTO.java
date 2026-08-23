package com.veiculo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record QuilometragemRequestDTO(

        @NotNull(message = "Quilometragem é obrigatória")
        @PositiveOrZero(message = "Quilometragem não pode ser negativa")
        Double quilometragem
) {
}
