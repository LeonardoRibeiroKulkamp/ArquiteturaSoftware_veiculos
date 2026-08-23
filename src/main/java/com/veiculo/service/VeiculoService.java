package com.veiculo.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.veiculo.data.QuilometragemData;
import com.veiculo.data.VeiculoData;
import com.veiculo.dto.QuilometragemRequestDTO;
import com.veiculo.dto.VeiculoRequestDTO;
import com.veiculo.exception.BusinessException;
import com.veiculo.exception.ResourceNotFoundException;
import com.veiculo.model.Marca;
import com.veiculo.model.RegistroQuilometragem;
import com.veiculo.model.Veiculo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final MarcaService marcaService;

    public List<Veiculo> listar(String placa, String modelo, Long marcaId) {
        return VeiculoData.VEICULOS.stream()
                .filter(veiculo -> placa == null || veiculo.getPlaca().equalsIgnoreCase(placa))
                .filter(veiculo -> modelo == null || veiculo.getModelo().toLowerCase().contains(modelo.toLowerCase()))
                .filter(veiculo -> marcaId == null || marcaId.equals(veiculo.getMarcaId()))
                .toList();
    }

    public Veiculo buscarPorId(Long id) {
        return VeiculoData.VEICULOS.stream()
                .filter(veiculo -> veiculo.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado com id " + id));
    }

    public Veiculo criar(VeiculoRequestDTO dto) {
        validarMarcaAtiva(dto.marcaId());
        validarPlacaDisponivel(dto.placa(), null);

        Veiculo veiculo = Veiculo.builder()
                .id(VeiculoData.SEQUENCE.incrementAndGet())
                .placa(dto.placa())
                .modelo(dto.modelo())
                .ano(dto.ano())
                .marcaId(dto.marcaId())
                .quilometragemAtual(dto.quilometragemAtual() != null ? dto.quilometragemAtual() : 0.0)
                .build();

        VeiculoData.VEICULOS.add(veiculo);
        return veiculo;
    }

    public Veiculo atualizar(Long id, VeiculoRequestDTO dto) {
        Veiculo veiculo = buscarPorId(id);
        validarMarcaAtiva(dto.marcaId());
        validarPlacaDisponivel(dto.placa(), id);

        veiculo.setPlaca(dto.placa());
        veiculo.setModelo(dto.modelo());
        veiculo.setAno(dto.ano());
        veiculo.setMarcaId(dto.marcaId());
        if (dto.quilometragemAtual() != null) {
            veiculo.setQuilometragemAtual(dto.quilometragemAtual());
        }
        return veiculo;
    }

    public void excluir(Long id) {
        Veiculo veiculo = buscarPorId(id);
        VeiculoData.VEICULOS.remove(veiculo);
        QuilometragemData.REGISTROS.removeIf(registro -> registro.getVeiculoId().equals(id));
    }

    public RegistroQuilometragem registrarQuilometragem(Long id, QuilometragemRequestDTO dto) {
        Veiculo veiculo = buscarPorId(id);

        if (dto.quilometragem() < veiculo.getQuilometragemAtual()) {
            throw new BusinessException("A nova quilometragem (" + dto.quilometragem()
                    + ") não pode ser menor que a quilometragem atual do veículo (" + veiculo.getQuilometragemAtual() + ")");
        }

        veiculo.setQuilometragemAtual(dto.quilometragem());

        RegistroQuilometragem registro = RegistroQuilometragem.builder()
                .id(QuilometragemData.SEQUENCE.incrementAndGet())
                .veiculoId(id)
                .quilometragem(dto.quilometragem())
                .dataRegistro(LocalDateTime.now())
                .build();

        QuilometragemData.REGISTROS.add(registro);
        return registro;
    }

    public List<RegistroQuilometragem> historicoQuilometragem(Long id) {
        buscarPorId(id);
        return QuilometragemData.REGISTROS.stream()
                .filter(registro -> registro.getVeiculoId().equals(id))
                .sorted(Comparator.comparing(RegistroQuilometragem::getDataRegistro))
                .toList();
    }

    private void validarMarcaAtiva(Long marcaId) {
        Marca marca = marcaService.buscarPorId(marcaId);
        if (!Boolean.TRUE.equals(marca.getAtiva())) {
            throw new BusinessException(
                    "A marca '" + marca.getNome() + "' está inativa e não pode ser associada a um veículo");
        }
    }

    private void validarPlacaDisponivel(String placa, Long idIgnorado) {
        boolean placaEmUso = VeiculoData.VEICULOS.stream()
                .anyMatch(veiculo -> !veiculo.getId().equals(idIgnorado) && veiculo.getPlaca().equalsIgnoreCase(placa));
        if (placaEmUso) {
            throw new BusinessException("Já existe um veículo cadastrado com a placa '" + placa + "'");
        }
    }
}
