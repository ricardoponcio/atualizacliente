package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.CriarProjetoAtualizacaoRequestDTO;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import dev.poncio.atualizacliente.repositories.IProjetoAtualizacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProjetoAtualizacaoService {

    @Autowired
    private IProjetoAtualizacaoRepository projetoAtualizacaoRepository;

    @Autowired
    private ProjetoAtualizacaoEmailService projetoAtualizacaoEmailService;

    public List<ProjetoAtualizacaoEntity> atualizacaoPorProjeto(Long projetoId) {
        return this.projetoAtualizacaoRepository.findAllByProjetoId(projetoId);
    }

    public ProjetoAtualizacaoEntity atualizacaoBuscaPorToken(String token) {
        return this.projetoAtualizacaoRepository.findByTokenView(token).orElseThrow(EntityNotFoundException::new);
    }

    public ProjetoAtualizacaoEntity inserirAtualizacao(CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO, ProjetoEntity projeto, UsuarioEntity usuarioLogado) {
        return inserirAtualizacao(ProjetoAtualizacaoEntity.builder()
                .projeto(projeto)
                .criadoEm(LocalDateTime.now())
                .criadoPor(usuarioLogado)
                .titulo(projeto.getNome())
                .descricao(criarProjetoAtualizacaoRequestDTO.getDescricao())
                .status(ProjetoEntity.ProjetoStatus.valueOfDesc(criarProjetoAtualizacaoRequestDTO.getStatus()))
                .subStatus(ProjetoEntity.ProjetoSubStatus.valueOfDesc(criarProjetoAtualizacaoRequestDTO.getSubStatus()))
                .tokenView(UUID.randomUUID().toString()).build());
    }

    private ProjetoAtualizacaoEntity inserirAtualizacao(ProjetoAtualizacaoEntity projetoAtualizacao) {
        ProjetoAtualizacaoEntity projetoAtualizacaoCriado = this.projetoAtualizacaoRepository.save(projetoAtualizacao);
        this.projetoAtualizacaoEmailService.registraIntencaoEmail(projetoAtualizacaoCriado);
        return projetoAtualizacaoCriado;
    }

}
