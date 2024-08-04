package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.AtualizarConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.dto.CriarConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.entities.ConfiguracaoEmailEntity;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.repositories.IConfiguracaoEmailRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.ConfiguracaoEmailMapper;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConfiguracaoEmailService {

    @Autowired
    private IConfiguracaoEmailRepository configuracaoEmailRepository;
    @Autowired
    private ConfiguracaoEmailMapper configuracaoEmailMapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private AuthContext authContext;

    public ConfiguracaoEmailEntity buscarPeloId(Long id) {
        return this.configuracaoEmailRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ConfiguracaoEmailEntity> listarConfiguracoes() {
        return this.configuracaoEmailRepository.findAll();
    }

    public ConfiguracaoEmailEntity inserirConfiguracao(CriarConfiguracaoEmailDTO criarConfiguracaoEmailDTO) throws RegraNegocioException {
        if (!listarConfiguracoes().isEmpty())
            throw new RegraNegocioException("Só pode existir uma configuração de e-mail ativa");
        ConfiguracaoEmailEntity configuracaoNova = configuracaoEmailMapper.map(criarConfiguracaoEmailDTO);
        configuracaoNova.setCriadoEm(LocalDateTime.now());
        configuracaoNova.setCriadoPor(authContext.getUsuarioLogado());
        return this.configuracaoEmailRepository.save(configuracaoNova);
    }

    public ConfiguracaoEmailEntity atualizarConfiguracao(Long id, AtualizarConfiguracaoEmailDTO atualizarConfiguracaoEmailDTO) {
        ConfiguracaoEmailEntity configuracaoSalva = buscarPeloId(id);
        ConfiguracaoEmailEntity configuracaoAlteracoes = configuracaoEmailMapper.map(atualizarConfiguracaoEmailDTO);
        partialUpdateMapper.map(configuracaoAlteracoes, configuracaoSalva);
        return this.configuracaoEmailRepository.save(configuracaoSalva);
    }

    public void removerConfiguracao(Long id) {
        if (!this.configuracaoEmailRepository.existsById(id))
            throw new EntityNotFoundException();

        this.configuracaoEmailRepository.deleteById(id);
    }

    public ConfiguracaoEmailEntity get() {
        List<ConfiguracaoEmailEntity> configuracoes = this.configuracaoEmailRepository.findAll();
        if (configuracoes.size() == 1) {
            return configuracoes.get(0);
        }
        return null;
    }

}
