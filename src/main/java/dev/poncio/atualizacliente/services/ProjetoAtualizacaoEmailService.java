package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEmailEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.repositories.IProjetoAtualizacaoEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjetoAtualizacaoEmailService {

    @Autowired
    private IProjetoAtualizacaoEmailRepository projetoAtualizacaoEmailRepository;

    public ProjetoAtualizacaoEmailEntity registraIntencaoEmail(ProjetoAtualizacaoEntity projetoAtualizacao) {
        return this.registraIntencaoEmail(
                ProjetoAtualizacaoEmailEntity.builder()
                        .assunto("Atualização do Projeto")
                        .corpo("Cheque na plataforma")
                        .envioSolicitadoEm(LocalDateTime.now())
                        .emailDestino(projetoAtualizacao.getProjeto().getCliente().getEmail())
                        .projetoAtualizacao(projetoAtualizacao)
                        .build());
    }

    private ProjetoAtualizacaoEmailEntity registraIntencaoEmail(ProjetoAtualizacaoEmailEntity projetoAtualizacaoEmail) {
        return this.projetoAtualizacaoEmailRepository.save(projetoAtualizacaoEmail);
    }

}
