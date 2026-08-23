package com.veiculo.dto;

import java.time.LocalDateTime;

public record QuilometragemResponseDTO(
        Long id,
        Long veiculoId,
        Double quilometragem,
        LocalDateTime dataRegistro
) {
}
