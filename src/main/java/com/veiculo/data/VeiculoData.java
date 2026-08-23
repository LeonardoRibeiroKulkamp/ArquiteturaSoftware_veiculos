package com.veiculo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.veiculo.model.Veiculo;

/**
 * Massa de dados fixa que substitui o acesso a um banco de dados real.
 * As demais camadas leem e escrevem diretamente nesta lista em memória.
 */
public class VeiculoData {

    public static final List<Veiculo> VEICULOS = new ArrayList<>(List.of(
            new Veiculo(1L, "ABC1D23", "Gol", 2020, 1L, 15000.0),
            new Veiculo(2L, "XYZ9E88", "Toro", 2022, 2L, 8000.0),
            new Veiculo(3L, "BRA2E19", "Onix", 2021, 3L, 22000.0),
            new Veiculo(4L, "CRV4F56", "Corolla", 2023, 5L, 5000.0),
            new Veiculo(5L, "HND8J31", "Civic", 2019, 6L, 41000.0),
            new Veiculo(6L, "HYU3K77", "HB20", 2022, 7L, 12500.0),
            new Veiculo(7L, "NIS6L42", "Kicks", 2021, 9L, 30000.0),
            new Veiculo(8L, "JEE9M15", "Renegade", 2020, 11L, 27800.0),
            new Veiculo(9L, "VWG1N88", "Polo", 2023, 1L, 3000.0),
            new Veiculo(10L, "FIA5P24", "Argo", 2018, 2L, 58000.0),
            new Veiculo(11L, "FRD7Q60", "Ka", 2017, 4L, 76000.0),
            new Veiculo(12L, "CHV2R33", "Tracker", 2022, 3L, 9800.0)
    ));

    public static final AtomicLong SEQUENCE = new AtomicLong(VEICULOS.size());

    private VeiculoData() {
    }
}
