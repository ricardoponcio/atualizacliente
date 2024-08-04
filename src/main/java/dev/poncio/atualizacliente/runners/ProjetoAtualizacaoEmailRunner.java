package dev.poncio.atualizacliente.runners;

import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.services.ConfiguracaoEmailService;
import dev.poncio.atualizacliente.services.ProjetoAtualizacaoEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProjetoAtualizacaoEmailRunner {

    @Autowired
    private ProjetoAtualizacaoEmailService projetoAtualizacaoEmailService;

    @Autowired
    private ConfiguracaoEmailService configuracaoEmailService;

    @Autowired
    private EnvioEmailRunner envioEmailRunner;

    @Scheduled(fixedDelay = 60000)
    public void enviarEmails() {
        try {
            final var configuracaoEmail = this.configuracaoEmailService.get();
            if (configuracaoEmail == null) throw new RegraNegocioException("Configuração de e-mail não cadastrada");
            final var emailPendenteLista = this.projetoAtualizacaoEmailService.listarPendentes();
            emailPendenteLista.stream().forEach(emailPendente -> {
                try {
                    envioEmailRunner.enviarEmailAtualizacaoProjeto(configuracaoEmail, emailPendente);
                    this.projetoAtualizacaoEmailService.atualizaSucessoPosEnvioEmail(emailPendente, configuracaoEmail);
                } catch (Exception e) {
                    this.projetoAtualizacaoEmailService.atualizaErroPosEnvioEmail(emailPendente, configuracaoEmail, e);
                    log.error("Erro ao enviar email ID " + emailPendente.getId());
                }
            });
        } catch (Exception e) {
            log.error("Falha ao realizar envio de emails", e);
        }
    }

}
