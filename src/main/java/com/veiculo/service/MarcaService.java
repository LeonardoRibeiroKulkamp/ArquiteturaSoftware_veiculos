package com.veiculo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veiculo.data.MarcaData;
import com.veiculo.data.VeiculoData;
import com.veiculo.dto.MarcaRequestDTO;
import com.veiculo.exception.BusinessException;
import com.veiculo.exception.ResourceNotFoundException;
import com.veiculo.model.Marca;

@Service
public class MarcaService {

    public List<Marca> listar(String nome, Boolean ativa) {
        return MarcaData.MARCAS.stream()
                .filter(marca -> nome == null || marca.getNome().toLowerCase().contains(nome.toLowerCase()))
                .filter(marca -> ativa == null || ativa.equals(marca.getAtiva()))
                .toList();
    }

    public Marca buscarPorId(Long id) {
        return MarcaData.MARCAS.stream()
                .filter(marca -> marca.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Marca não encontrada com id " + id));
    }

    public Marca criar(MarcaRequestDTO dto) {
        validarNomeDisponivel(dto.nome(), null);

        Marca marca = Marca.builder()
                .id(MarcaData.SEQUENCE.incrementAndGet())
                .nome(dto.nome())
                .ativa(dto.ativa())
                .build();

        MarcaData.MARCAS.add(marca);
        return marca;
    }

    public Marca atualizar(Long id, MarcaRequestDTO dto) {
        Marca marca = buscarPorId(id);
        validarNomeDisponivel(dto.nome(), id);

        marca.setNome(dto.nome());
        marca.setAtiva(dto.ativa());
        return marca;
    }

    public void excluir(Long id) {
        Marca marca = buscarPorId(id);

        boolean marcaEmUso = VeiculoData.VEICULOS.stream()
                .anyMatch(veiculo -> veiculo.getMarcaId().equals(id));
        if (marcaEmUso) {
            throw new BusinessException(
                    "Não é possível excluir a marca '" + marca.getNome() + "' pois há veículos associados a ela");
        }

        MarcaData.MARCAS.remove(marca);
    }

    private void validarNomeDisponivel(String nome, Long idIgnorado) {
        boolean nomeEmUso = MarcaData.MARCAS.stream()
                .anyMatch(marca -> !marca.getId().equals(idIgnorado) && marca.getNome().equalsIgnoreCase(nome));
        if (nomeEmUso) {
            throw new BusinessException("Já existe uma marca cadastrada com o nome '" + nome + "'");
        }
    }
}
