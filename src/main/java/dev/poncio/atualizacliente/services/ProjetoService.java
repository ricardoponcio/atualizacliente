package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEmailEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.repositories.IProjetoAtualizacaoEmailRepository;
import dev.poncio.atualizacliente.repositories.IProjetoAtualizacaoRepository;
import dev.poncio.atualizacliente.repositories.IProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private IProjetoRepository projetoRepository;
    @Autowired
    private IProjetoAtualizacaoRepository projetoAtualizacaoRepository;
    @Autowired
    private IProjetoAtualizacaoEmailRepository projetoAtualizacaoEmailRepository;

    public List<ProjetoEntity> listarProjetos() {
        return this.projetoRepository.findAll();
    }

    public ProjetoEntity inserirProjeto(ProjetoEntity projeto) {
        projeto.setStatus(ProjetoEntity.ProjetoStatus.A);
        projeto.setSubStatus(ProjetoEntity.ProjetoSubStatus.F);
        projeto.setCriadoEm(LocalDateTime.now());
        return this.projetoRepository.save(projeto);
    }

    public ProjetoEntity atualizarProjeto(Long id, ProjetoEntity projeto) {
        ProjetoEntity projetoSalvo = this.projetoRepository.findById(id).orElse(null);
        if (projetoSalvo == null)
            throw new EntityNotFoundException();

        partialUpdateMapper.map(projeto, projetoSalvo);
        return this.projetoRepository.save(projetoSalvo);
    }

    public void removerProjeto(Long id) {
        if (!this.projetoRepository.existsById(id))
            throw new EntityNotFoundException();

        this.projetoRepository.deleteById(id);
    }

    public ProjetoAtualizacaoEntity inserirAtalizacao(ProjetoAtualizacaoEntity projetoAtualizacao) {
        ProjetoAtualizacaoEntity projetoAtualizacaoCriado = this.projetoAtualizacaoRepository.save(projetoAtualizacao);
        registraIntencaoEmail(
                ProjetoAtualizacaoEmailEntity.builder()
                        .assunto("Atualização do Projeto")
                        .corpo("Cheque na plataforma")
                        .emailSolicitadoEm(LocalDateTime.now())
                        .emailDestino(projetoAtualizacao.getProjeto().getCliente().getEmail())
                        .build());
        return projetoAtualizacaoCriado;
    }

    private ProjetoAtualizacaoEmailEntity registraIntencaoEmail(ProjetoAtualizacaoEmailEntity projetoAtualizacaoEmail) {
        return this.projetoAtualizacaoEmailRepository.save(projetoAtualizacaoEmail);
    }

}
