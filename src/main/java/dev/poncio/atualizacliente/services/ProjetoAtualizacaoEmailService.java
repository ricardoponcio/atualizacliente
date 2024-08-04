package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.entities.ConfiguracaoEmailEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEmailEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.repositories.IProjetoAtualizacaoEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjetoAtualizacaoEmailService {

    @Autowired
    private IProjetoAtualizacaoEmailRepository projetoAtualizacaoEmailRepository;

    public ProjetoAtualizacaoEmailEntity registraIntencaoEmail(ProjetoAtualizacaoEntity projetoAtualizacao) {
        String link = String.format("http://localhost:8080/projetos/listar/%s/atualizacoes/token", projetoAtualizacao.getTokenView());
        return this.registraIntencaoEmail(
                ProjetoAtualizacaoEmailEntity.builder()
                        .assunto("Atualização do Projeto")
                        .corpo(String.format("Cheque na plataforma: %s", link))
                        .envioSolicitadoEm(LocalDateTime.now())
                        .emailDestino(projetoAtualizacao.getProjeto().getCliente().getEmail())
                        .projetoAtualizacao(projetoAtualizacao)
                        .build());
    }

    private ProjetoAtualizacaoEmailEntity registraIntencaoEmail(ProjetoAtualizacaoEmailEntity projetoAtualizacaoEmail) {
        return this.projetoAtualizacaoEmailRepository.save(projetoAtualizacaoEmail);
    }

    public List<ProjetoAtualizacaoEmailEntity> listarPendentes() {
        return this.projetoAtualizacaoEmailRepository.findAllByEnvioProcessadoEmIsNull();
    }

    public void atualizaSucessoPosEnvioEmail(ProjetoAtualizacaoEmailEntity projetoAtualizacaoEmail, ConfiguracaoEmailEntity configuracaoEmail) {
        mergeData(configuracaoEmail, projetoAtualizacaoEmail);
        projetoAtualizacaoEmail.setEnvioProcessadoEm(LocalDateTime.now());
        projetoAtualizacaoEmail.setResultado(ProjetoAtualizacaoEmailEntity.ProjetoAtualizacaoEmailResultado.S);
        this.projetoAtualizacaoEmailRepository.save(projetoAtualizacaoEmail);
    }

    public void atualizaErroPosEnvioEmail(ProjetoAtualizacaoEmailEntity projetoAtualizacaoEmail, ConfiguracaoEmailEntity configuracaoEmail, Exception e) {
        mergeData(configuracaoEmail, projetoAtualizacaoEmail);
        projetoAtualizacaoEmail.setEnvioProcessadoEm(LocalDateTime.now());
        projetoAtualizacaoEmail.setResultado(ProjetoAtualizacaoEmailEntity.ProjetoAtualizacaoEmailResultado.F);
        projetoAtualizacaoEmail.setMensagemErro(e.getLocalizedMessage());
        this.projetoAtualizacaoEmailRepository.save(projetoAtualizacaoEmail);
    }

    private void mergeData(ConfiguracaoEmailEntity configuracaoEmail, ProjetoAtualizacaoEmailEntity projetoAtualizacaoEmail) {
        projetoAtualizacaoEmail.setSmtpHost(configuracaoEmail.getSmtpHost());
        projetoAtualizacaoEmail.setSmtpPort(configuracaoEmail.getSmtpPort());
        projetoAtualizacaoEmail.setSmtpAuth(configuracaoEmail.getSmtpAuth());
        projetoAtualizacaoEmail.setSmtpSsl(configuracaoEmail.getSmtpSsl());
    }

}
