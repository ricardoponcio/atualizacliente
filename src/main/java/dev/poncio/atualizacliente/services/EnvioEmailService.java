package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.EnvioEmailStatusDTO;
import dev.poncio.atualizacliente.entities.*;
import dev.poncio.atualizacliente.repositories.IEnvioEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EnvioEmailService {

    @Autowired
    private IEnvioEmailRepository envioEmailRepository;

    public EnvioEmailEntity enviaEmail(String assunto, String corpo, String emailDestino, ProjetoAtualizacaoEntity projetoAtualizacao) {
        return this.registraIntencaoEmail(this.preparaEnvioEmail(assunto, corpo, emailDestino).projetoAtualizacao(projetoAtualizacao).build());
    }

    public EnvioEmailEntity enviaEmail(String assunto, String corpo, String emailDestino, ProjetoEntity projeto) {
        return this.registraIntencaoEmail(this.preparaEnvioEmail(assunto, corpo, emailDestino).projeto(projeto).build());
    }

    public EnvioEmailEntity enviaEmail(String assunto, String corpo, String emailDestino, ClienteEntity cliente) {
        return this.registraIntencaoEmail(this.preparaEnvioEmail(assunto, corpo, emailDestino).cliente(cliente).build());
    }

    public EnvioEmailEntity enviaEmail(String assunto, String corpo, String emailDestino, UsuarioEntity usuario) {
        return this.registraIntencaoEmail(this.preparaEnvioEmail(assunto, corpo, emailDestino).usuario(usuario).build());
    }

    private EnvioEmailEntity.EnvioEmailEntityBuilder preparaEnvioEmail(String assunto, String corpo, String emailDestino) {
        return EnvioEmailEntity.builder()
                .assunto(assunto)
                .corpo(corpo)
                .envioSolicitadoEm(LocalDateTime.now())
                .emailDestino(emailDestino)
                .tipo(EnvioEmailEntity.EnvioEmailTipo.PROJETO_ATUALIZACAO);
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
        envioEmail.setResultado(EnvioEmailEntity.EnvioEmailResultado.ENVIADO_SUCESSO);
        this.envioEmailRepository.save(envioEmail);
    }

    public void atualizaErroPosEnvioEmail(EnvioEmailEntity envioEmail, ConfiguracaoEmailEntity configuracaoEmail, Exception e) {
        mergeData(configuracaoEmail, envioEmail);
        envioEmail.setEnvioProcessadoEm(LocalDateTime.now());
        envioEmail.setResultado(EnvioEmailEntity.EnvioEmailResultado.ENVIO_FALHOU);
        envioEmail.setMensagemErro(e.getLocalizedMessage());
        this.envioEmailRepository.save(envioEmail);
    }

    private void mergeData(ConfiguracaoEmailEntity configuracaoEmail, EnvioEmailEntity envioEmail) {
        envioEmail.setSmtpHost(configuracaoEmail.getSmtpHost());
        envioEmail.setSmtpPort(configuracaoEmail.getSmtpPort());
        envioEmail.setSmtpAuth(configuracaoEmail.getSmtpAuth());
        envioEmail.setSmtpSsl(configuracaoEmail.getSmtpSsl());
        envioEmail.setEnviadoDe(configuracaoEmail.getEnviarDe());
    }

    public List<EnvioEmailEntity> ultimosEmailsProcessados() {
        return this.envioEmailRepository.findTop10ByEnvioProcessadoEmIsNotNullOrderByIdDesc();
    }

    public EnvioEmailStatusDTO statusEnvios() {
        Optional<EnvioEmailEntity> ultimoEnvio = this.envioEmailRepository.findTopByEnvioProcessadoEmIsNotNullOrderByIdDesc();
        Optional<EnvioEmailEntity> ultimoSucesso = this.envioEmailRepository.findTopByResultadoAndEnvioProcessadoEmIsNotNullOrderByIdDesc(EnvioEmailEntity.EnvioEmailResultado.ENVIADO_SUCESSO);

        return EnvioEmailStatusDTO.builder()
                .funcionando(ultimoEnvio.map(envio -> envio.getResultado() == EnvioEmailEntity.EnvioEmailResultado.ENVIADO_SUCESSO).orElse(true))
                .ultimaUtilizacao(ultimoEnvio.map(EnvioEmailEntity::getEnvioProcessadoEm).orElse(null))
                .ultimaUtilizacaoSucesso(ultimoSucesso.map(EnvioEmailEntity::getEnvioProcessadoEm).orElse(null))
                .build();
    }

}
