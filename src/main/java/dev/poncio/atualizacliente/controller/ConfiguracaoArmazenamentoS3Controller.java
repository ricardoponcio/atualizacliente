package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.AtualizarConfiguracaoArmazenamentoS3DTO;
import dev.poncio.atualizacliente.dto.ConfiguracaoArmazenamentoS3lDTO;
import dev.poncio.atualizacliente.dto.CriarConfiguracaoArmazenamentoS3DTO;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.services.ConfiguracaoArmazenamentoS3Service;
import dev.poncio.atualizacliente.utils.ConfiguracaoArmazenamentoS3Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/configuracao-s3")
public class ConfiguracaoArmazenamentoS3Controller {

    @Autowired
    private ConfiguracaoArmazenamentoS3Mapper configuracaoArmazenamentoS3Mapper;

    @Autowired
    private ConfiguracaoArmazenamentoS3Service configuracaoArmazenamentoS3Service;

    @GetMapping("/listar")
    public List<ConfiguracaoArmazenamentoS3lDTO> listarConfiguracoes() {
        return this.configuracaoArmazenamentoS3Service.listarConfiguracoes().stream().map(configuracaoArmazenamentoS3Mapper::map).collect(Collectors.toList());
    }

    @PutMapping("/criar")
    public ConfiguracaoArmazenamentoS3lDTO inserirConfiguracao(@RequestBody CriarConfiguracaoArmazenamentoS3DTO criarConfiguracaoArmazenamentoS3DTO) throws RegraNegocioException {
        return configuracaoArmazenamentoS3Mapper.map(this.configuracaoArmazenamentoS3Service.inserirConfiguracao(criarConfiguracaoArmazenamentoS3DTO));
    }

    @PatchMapping("/atualizar/{id}")
    public ConfiguracaoArmazenamentoS3lDTO atualizarConfiguracao(@PathVariable Long id, @RequestBody AtualizarConfiguracaoArmazenamentoS3DTO atualizarConfiguracaoArmazenamentoS3DTO) {
        return configuracaoArmazenamentoS3Mapper.map(this.configuracaoArmazenamentoS3Service.atualizarConfiguracao(id, atualizarConfiguracaoArmazenamentoS3DTO));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerConfiguracao(@PathVariable Long id) {
        this.configuracaoArmazenamentoS3Service.removerConfiguracao(id);
        return ResponseEntity.ok().build();
    }

}
