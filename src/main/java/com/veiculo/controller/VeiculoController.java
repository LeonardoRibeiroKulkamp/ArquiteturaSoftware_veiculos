package com.veiculo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.veiculo.dto.MarcaResponseDTO;
import com.veiculo.dto.QuilometragemRequestDTO;
import com.veiculo.dto.QuilometragemResponseDTO;
import com.veiculo.dto.VeiculoRequestDTO;
import com.veiculo.dto.VeiculoResponseDTO;
import com.veiculo.model.Marca;
import com.veiculo.model.RegistroQuilometragem;
import com.veiculo.model.Veiculo;
import com.veiculo.service.MarcaService;
import com.veiculo.service.VeiculoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final MarcaService marcaService;

    @GetMapping
    public List<VeiculoResponseDTO> listar(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Long marcaId) {
        return veiculoService.listar(placa, modelo, marcaId).stream().map(this::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public VeiculoResponseDTO buscarPorId(@PathVariable Long id) {
        return toResponseDTO(veiculoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponseDTO criar(@Valid @RequestBody VeiculoRequestDTO dto) {
        return toResponseDTO(veiculoService.criar(dto));
    }

    @PutMapping("/{id}")
    public VeiculoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoRequestDTO dto) {
        return toResponseDTO(veiculoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        veiculoService.excluir(id);
    }

    @PostMapping("/{id}/quilometragem")
    @ResponseStatus(HttpStatus.CREATED)
    public QuilometragemResponseDTO registrarQuilometragem(
            @PathVariable Long id, @Valid @RequestBody QuilometragemRequestDTO dto) {
        return toResponseDTO(veiculoService.registrarQuilometragem(id, dto));
    }

    @GetMapping("/{id}/quilometragem")
    public List<QuilometragemResponseDTO> historicoQuilometragem(@PathVariable Long id) {
        return veiculoService.historicoQuilometragem(id).stream().map(this::toResponseDTO).toList();
    }

    private VeiculoResponseDTO toResponseDTO(Veiculo veiculo) {
        Marca marca = marcaService.buscarPorId(veiculo.getMarcaId());
        MarcaResponseDTO marcaDTO = new MarcaResponseDTO(marca.getId(), marca.getNome(), marca.getAtiva());
        return new VeiculoResponseDTO(
                veiculo.getId(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getAno(), marcaDTO,
                veiculo.getQuilometragemAtual());
    }

    private QuilometragemResponseDTO toResponseDTO(RegistroQuilometragem registro) {
        return new QuilometragemResponseDTO(
                registro.getId(), registro.getVeiculoId(), registro.getQuilometragem(), registro.getDataRegistro());
    }
}
