package com.veiculo.dto;

public record VeiculoResponseDTO(
        Long id,
        String placa,
        String modelo,
        Integer ano,
        MarcaResponseDTO marca,
        Double quilometragemAtual
) {
}
