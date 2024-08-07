package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.entities.ConfiguracaoEmailEntity;
import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.repositories.IEnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvioEmailService {

    @Autowired
    private IEnvioEmailRepository envioEmailRepository;

    public EnvioEmailEntity registraIntencaoEmail(ProjetoAtualizacaoEntity projetoAtualizacao) {
        String link = String.format("Veja na aplicação https://localhost:8081/atualizacao?__token_visualizacao_atualizacao=%s", projetoAtualizacao.getTokenView());
        return this.registraIntencaoEmail(
                EnvioEmailEntity.builder()
                        .assunto("Atualização do Projeto - " + projetoAtualizacao.getTitulo())
                        .corpo(String.format("Cheque na plataforma: %s", link))
                        .envioSolicitadoEm(LocalDateTime.now())
                        .emailDestino(projetoAtualizacao.getProjeto().getCliente().getEmail())
                        .projetoAtualizacao(projetoAtualizacao)
                        .tipo(EnvioEmailEntity.EnvioEmailTipo.PROJETO_ATUALIZACAO)
                        .build());
    }

    private EnvioEmailEntity registraIntencaoEmail(EnvioEmailEntity projetoAtualizacaoEmail) {
        return this.envioEmailRepository.save(projetoAtualizacaoEmail);
    }

    public List<EnvioEmailEntity> listarPendentes() {
        return this.envioEmailRepository.findAllByEnvioProcessadoEmIsNull();
    }

    public void atualizaSucessoPosEnvioEmail(EnvioEmailEntity envioEmail, ConfiguracaoEmailEntity configuracaoEmail) {
        mergeData(configuracaoEmail, envioEmail);
        envioEmail.setEnvioProcessadoEm(LocalDateTime.now());
        envioEmail.setResultado(EnvioEmailEntity.EnvioEmailResultado.S);
        this.envioEmailRepository.save(envioEmail);
    }

    public void atualizaErroPosEnvioEmail(EnvioEmailEntity envioEmail, ConfiguracaoEmailEntity configuracaoEmail, Exception e) {
        mergeData(configuracaoEmail, envioEmail);
        envioEmail.setEnvioProcessadoEm(LocalDateTime.now());
        envioEmail.setResultado(EnvioEmailEntity.EnvioEmailResultado.F);
        envioEmail.setMensagemErro(e.getLocalizedMessage());
        this.envioEmailRepository.save(envioEmail);
    }

    private void mergeData(ConfiguracaoEmailEntity configuracaoEmail, EnvioEmailEntity envioEmail) {
        envioEmail.setSmtpHost(configuracaoEmail.getSmtpHost());
        envioEmail.setSmtpPort(configuracaoEmail.getSmtpPort());
        envioEmail.setSmtpAuth(configuracaoEmail.getSmtpAuth());
        envioEmail.setSmtpSsl(configuracaoEmail.getSmtpSsl());
    }

    public List<EnvioEmailEntity> ultimosEmailsProcessados() {
        return this.envioEmailRepository.findTop10ByEnvioProcessadoEmIsNotNullOrderByIdDesc();
    }

}
