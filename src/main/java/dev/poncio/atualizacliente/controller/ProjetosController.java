package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.AtualizaProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.ProjetoAtualizacaoDTO;
import dev.poncio.atualizacliente.dto.ProjetoDTO;
import dev.poncio.atualizacliente.services.ProjetoAtualizacaoService;
import dev.poncio.atualizacliente.services.ProjetoService;
import dev.poncio.atualizacliente.utils.ProjetoAtualizacaoMapper;
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
    private ProjetoAtualizacaoMapper projetoAtualizacaoMapper;

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private ProjetoAtualizacaoService projetoAtualizacaoService;

    @GetMapping("/listar")
    public List<ProjetoDTO> listarProjetos() {
        return this.projetoService.listarProjetos().stream().map(projetoMapper::map).collect(Collectors.toList());
    }

    @PutMapping("/criar")
    public ProjetoDTO criarProjeto(@RequestBody CriarProjetoRequestDTO criarProjetoRequestDTO) {
        return projetoMapper.map(this.projetoService.inserirProjeto(criarProjetoRequestDTO));
    }

    @PatchMapping("/atualizar/{id}")
    public ProjetoDTO atualizarProjeto(@PathVariable Long id, @RequestBody AtualizaProjetoRequestDTO atualizaProjetoRequestDTO) {
        return projetoMapper.map(this.projetoService.atualizarProjeto(id, atualizaProjetoRequestDTO));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removeProjeto(@PathVariable Long id) {
        this.projetoService.removerProjeto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{id}/atualizacoes")
    public List<ProjetoAtualizacaoDTO> listarAtualizacoesProjeto(@PathVariable Long id) {
        return this.projetoAtualizacaoService.atualizacaoPorProjeto(id)
                .stream().map(projetoAtualizacaoMapper::map).collect(Collectors.toList());
    }

}
