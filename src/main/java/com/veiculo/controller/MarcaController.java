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

import com.veiculo.dto.MarcaRequestDTO;
import com.veiculo.dto.MarcaResponseDTO;
import com.veiculo.model.Marca;
import com.veiculo.service.MarcaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public List<MarcaResponseDTO> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativa) {
        return marcaService.listar(nome, ativa).stream().map(this::toResponseDTO).toList();
    }

    @GetMapping("/{id}")
    public MarcaResponseDTO buscarPorId(@PathVariable Long id) {
        return toResponseDTO(marcaService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarcaResponseDTO criar(@Valid @RequestBody MarcaRequestDTO dto) {
        return toResponseDTO(marcaService.criar(dto));
    }

    @PutMapping("/{id}")
    public MarcaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody MarcaRequestDTO dto) {
        return toResponseDTO(marcaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        marcaService.excluir(id);
    }

    private MarcaResponseDTO toResponseDTO(Marca marca) {
        return new MarcaResponseDTO(marca.getId(), marca.getNome(), marca.getAtiva());
    }
}
