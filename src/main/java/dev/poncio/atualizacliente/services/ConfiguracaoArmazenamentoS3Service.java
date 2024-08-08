package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.AtualizarConfiguracaoArmazenamentoS3DTO;
import dev.poncio.atualizacliente.dto.CriarConfiguracaoArmazenamentoS3DTO;
import dev.poncio.atualizacliente.entities.ConfiguracaoArmazenamentoS3Entity;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.repositories.IConfiguracaoArmazenamentoS3Repository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.ConfiguracaoArmazenamentoS3Mapper;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConfiguracaoArmazenamentoS3Service {

    @Autowired
    private IConfiguracaoArmazenamentoS3Repository configuracaoArmazenamentoS3Repository;
    @Autowired
    private ConfiguracaoArmazenamentoS3Mapper configuracaoArmazenamentoS3Mapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private AuthContext authContext;

    public ConfiguracaoArmazenamentoS3Entity buscarPeloId(Long id) {
        return this.configuracaoArmazenamentoS3Repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ConfiguracaoArmazenamentoS3Entity> listarConfiguracoes() {
        return this.configuracaoArmazenamentoS3Repository.findAll();
    }

    public ConfiguracaoArmazenamentoS3Entity inserirConfiguracao(CriarConfiguracaoArmazenamentoS3DTO criarConfiguracaoArmazenamentoS3DTO) throws RegraNegocioException {
        if (!listarConfiguracoes().isEmpty())
            throw new RegraNegocioException("Só pode existir uma configuração de armazenamento de S3 ativa");
        ConfiguracaoArmazenamentoS3Entity configuracaoNova = configuracaoArmazenamentoS3Mapper.map(criarConfiguracaoArmazenamentoS3DTO);
        configuracaoNova.setCriadoEm(LocalDateTime.now());
        configuracaoNova.setCriadoPor(authContext.getUsuarioLogado());
        return this.configuracaoArmazenamentoS3Repository.save(configuracaoNova);
    }

    public ConfiguracaoArmazenamentoS3Entity atualizarConfiguracao(Long id, AtualizarConfiguracaoArmazenamentoS3DTO atualizarConfiguracaoArmazenamentoS3DTO) {
        ConfiguracaoArmazenamentoS3Entity configuracaoSalva = buscarPeloId(id);
        ConfiguracaoArmazenamentoS3Entity configuracaoAlteracoes = configuracaoArmazenamentoS3Mapper.map(atualizarConfiguracaoArmazenamentoS3DTO);
        partialUpdateMapper.map(configuracaoAlteracoes, configuracaoSalva);
        return this.configuracaoArmazenamentoS3Repository.save(configuracaoSalva);
    }

    public void removerConfiguracao(Long id) {
        if (!this.configuracaoArmazenamentoS3Repository.existsById(id))
            throw new EntityNotFoundException();

        this.configuracaoArmazenamentoS3Repository.deleteById(id);
    }

    public ConfiguracaoArmazenamentoS3Entity get() {
        List<ConfiguracaoArmazenamentoS3Entity> configuracoes = this.configuracaoArmazenamentoS3Repository.findAll();
        if (configuracoes.size() == 1) {
            return configuracoes.get(0);
        }
        return null;
    }

}
