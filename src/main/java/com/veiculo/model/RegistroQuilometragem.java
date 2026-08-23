package com.veiculo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroQuilometragem {

    private Long id;
    private Long veiculoId;
    private Double quilometragem;
    private LocalDateTime dataRegistro;
}
