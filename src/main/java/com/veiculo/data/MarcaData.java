package com.veiculo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.veiculo.model.Marca;

/**
 * Massa de dados fixa que substitui o acesso a um banco de dados real.
 * As demais camadas leem e escrevem diretamente nesta lista em memória.
 */
public class MarcaData {

    public static final List<Marca> MARCAS = new ArrayList<>(List.of(
            new Marca(1L, "Volkswagen", true),
            new Marca(2L, "Fiat", true),
            new Marca(3L, "Chevrolet", true),
            new Marca(4L, "Ford", false),
            new Marca(5L, "Toyota", true),
            new Marca(6L, "Honda", true),
            new Marca(7L, "Hyundai", true),
            new Marca(8L, "Renault", false),
            new Marca(9L, "Nissan", true),
            new Marca(10L, "Peugeot", false),
            new Marca(11L, "Jeep", true),
            new Marca(12L, "Citroën", false)
    ));

    public static final AtomicLong SEQUENCE = new AtomicLong(MARCAS.size());

    private MarcaData() {
    }
}
