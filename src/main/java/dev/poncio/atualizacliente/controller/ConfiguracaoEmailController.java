package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.AtualizarConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.dto.ConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.dto.CriarConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.services.ConfiguracaoEmailService;
import dev.poncio.atualizacliente.utils.ConfiguracaoEmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/configuracao-email")
public class ConfiguracaoEmailController {

    @Autowired
    private ConfiguracaoEmailMapper configuracaoEmailMapper;

    @Autowired
    private ConfiguracaoEmailService configuracaoEmailService;

    @GetMapping("/listar")
    public List<ConfiguracaoEmailDTO> listarConfiguracoes() {
        return this.configuracaoEmailService.listarConfiguracoes().stream().map(configuracaoEmailMapper::map).collect(Collectors.toList());
    }

    @PutMapping("/criar")
    public ConfiguracaoEmailDTO inserirConfiguracao(@RequestBody CriarConfiguracaoEmailDTO criarConfiguracaoEmailDTO) throws RegraNegocioException {
        return configuracaoEmailMapper.map(this.configuracaoEmailService.inserirConfiguracao(criarConfiguracaoEmailDTO));
    }

    @PatchMapping("/atualizar/{id}")
    public ConfiguracaoEmailDTO atualizarConfiguracao(@PathVariable Long id, @RequestBody AtualizarConfiguracaoEmailDTO atualizarConfiguracaoEmailDTO) {
        return configuracaoEmailMapper.map(this.configuracaoEmailService.atualizarConfiguracao(id, atualizarConfiguracaoEmailDTO));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removerConfiguracao(@PathVariable Long id) {
        this.configuracaoEmailService.removerConfiguracao(id);
        return ResponseEntity.ok().build();
    }

}
