package com.veiculo.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.veiculo.model.RegistroQuilometragem;

/**
 * Histórico de leituras de quilometragem, mantido em memória.
 * Começa vazio: os registros são criados conforme os deslocamentos ocorrem.
 */
public class QuilometragemData {

    public static final List<RegistroQuilometragem> REGISTROS = new ArrayList<>();

    public static final AtomicLong SEQUENCE = new AtomicLong(0);

    private QuilometragemData() {
    }
}
