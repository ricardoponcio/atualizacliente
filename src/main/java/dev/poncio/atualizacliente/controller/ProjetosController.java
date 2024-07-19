package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.AtualizaProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.ProjetoDTO;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.services.ProjetoService;
import dev.poncio.atualizacliente.utils.ProjetoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projetos")
public class ProjetosController {

    @Autowired
    private ProjetoMapper projetoMapper;

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/listar")
    public List<ProjetoDTO> listarProjetos() {
        return this.projetoService.listarProjetos().stream().map(projetoMapper::map).collect(Collectors.toList());
    }

    @PutMapping("/criar")
    public ProjetoDTO criarProjeto(@RequestBody CriarProjetoRequestDTO criarProjetoRequestDTO) {
        ProjetoEntity novoProjeto = this.projetoService.inserirProjeto(projetoMapper.map(criarProjetoRequestDTO));
        return projetoMapper.map(novoProjeto);
    }

    @PatchMapping("/atualizar/{id}")
    public ProjetoDTO atualizarProjeto(@PathVariable Long id, @RequestBody AtualizaProjetoRequestDTO atualizaProjetoRequestDTO) {
        ProjetoEntity projetoAtualizado = this.projetoService.atualizarProjeto(id, projetoMapper.map(atualizaProjetoRequestDTO));
        return projetoMapper.map(projetoAtualizado);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removeProjeto(@PathVariable Long id) {
        this.projetoService.removerProjeto(id);
        return ResponseEntity.ok().build();
    }

}
