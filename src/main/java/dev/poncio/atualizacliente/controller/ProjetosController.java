package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.domain.ProjetoFiltro;
import dev.poncio.atualizacliente.dto.*;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.services.ProjetoService;
import dev.poncio.atualizacliente.utils.ProjetoAtualizacaoMapper;
import dev.poncio.atualizacliente.utils.ProjetoMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/projetos")
public class ProjetosController {

    @Autowired
    private ProjetoMapper projetoMapper;
    @Autowired
    private ProjetoAtualizacaoMapper projetoAtualizacaoMapper;

    @Autowired
    private ProjetoService projetoService;

    @GetMapping("/listar")
    public List<ProjetoDTO> listarProjetos(@RequestParam(required = false) ProjetoEntity.ProjetoStatus status, @RequestParam(required = false) ProjetoEntity.ProjetoSubStatus subStatus) {
        return this.projetoService.listarProjetos(
                        ProjetoFiltro.builder()
                                .status(status)
                                .subStatus(subStatus)
                                .build())
                .stream().map(projetoMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/estatisticas")
    public ProjetosEstatisticasDTO retornarEstatisticas() {
        return this.projetoService.retornarEstatisticas();
    }

    @GetMapping("/{id}/detalhe")
    public ProjetoDTO detalharProjeto(@PathVariable Long id) {
        return this.projetoMapper.map(this.projetoService.buscarPeloId(id));
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
        return this.projetoService.listarAtualizacoes(id)
                .stream().map(projetoAtualizacaoMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/atualizacao/{id}/detalhe")
    public ProjetoAtualizacaoComProjetoDTO detalharProjetoAtualizacao(@PathVariable("id") Long projetoAtualizacaoId) {
        return this.projetoAtualizacaoMapper.mapDetail(this.projetoService.detalharAtualizacao(projetoAtualizacaoId));
    }

    @PostMapping("/listar/{token}/atualizacoes/token")
    public ProjetoAtualizacaoComProjetoIdDTO listarAtualizacoesProjeto(@PathVariable String token, @RequestBody SenhaClienteRequestDTO senhaClienteRequestDTO) throws RegraNegocioException {
        return this.projetoAtualizacaoMapper.mapLessDetail(this.projetoService.retornarAtualizacaoPorToken(senhaClienteRequestDTO.getSenhaCliente(), token));
    }

    @PutMapping("/{projetoId}/atualizacoes/criar")
    public ProjetoAtualizacaoDTO inserirAtualizacao(@PathVariable Long projetoId, @RequestBody CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO) throws RegraNegocioException {
        return this.projetoAtualizacaoMapper.map(this.projetoService.emitirNovaAtualizacao(projetoId, criarProjetoAtualizacaoRequestDTO));
    }

    @PutMapping(value = "/{projetoId}/atualizacoes/criar/com-anexos", consumes = {MediaType.APPLICATION_PROBLEM_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ProjetoAtualizacaoDTO inserirAtualizacaoComAnexos(@PathVariable Long projetoId, @RequestPart("body") CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO, @RequestPart("anexo") MultipartFile[] anexo) throws RegraNegocioException {
        return this.projetoAtualizacaoMapper.map(this.projetoService.emitirNovaAtualizacaoComAnexos(projetoId, criarProjetoAtualizacaoRequestDTO, Stream.of(anexo).toList()));
    }

    @GetMapping(value = "/{projetoId}/atualizacao/{projetoAtualizacaoId}/baixar/{nomeArquivoUpload}")
    public ResponseEntity<InputStreamResource> baixarAnexoAtualizacao(@PathVariable Long projetoId, @PathVariable Long projetoAtualizacaoId, @PathVariable String nomeArquivoUpload, HttpServletResponse response) {
        DownloadArquivoDTO arquivo = this.projetoService.baixaAnexoProjetoAtualizacao(projetoId, projetoAtualizacaoId, nomeArquivoUpload);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", arquivo.getNomeArquivo()));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(arquivo.getContentType()))
                .body(new InputStreamResource(arquivo.getInputStream()));
    }

    @GetMapping(value = "/{projetoId}/atualizacao/{projetoAtualizacaoId}/baixar/{nomeArquivoUpload}/token/{token}")
    public ResponseEntity<InputStreamResource> baixarAnexoAtualizacao(@PathVariable Long projetoId, @PathVariable Long projetoAtualizacaoId, @PathVariable String nomeArquivoUpload, @PathVariable String token, HttpServletResponse response) {
        DownloadArquivoDTO arquivo = this.projetoService.baixaAnexoProjetoAtualizacaoToken(projetoId, projetoAtualizacaoId, nomeArquivoUpload, token);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", arquivo.getNomeArquivo()));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(arquivo.getContentType()))
                .body(new InputStreamResource(arquivo.getInputStream()));
    }

}
