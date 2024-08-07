package dev.poncio.atualizacliente.runners;

import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.services.ConfiguracaoEmailService;
import dev.poncio.atualizacliente.services.EnvioEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProjetoAtualizacaoEmailRunner {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private ConfiguracaoEmailService configuracaoEmailService;

    @Autowired
    private EnvioEmailRunner envioEmailRunner;

    @Scheduled(fixedDelay = 60000)
    public void enviarEmails() {
        try {
            final var configuracaoEmail = this.configuracaoEmailService.get();
            if (configuracaoEmail == null) return;
            final var emailPendenteLista = this.envioEmailService.listarPendentes();
            emailPendenteLista.stream().forEach(emailPendente -> {
                try {
                    envioEmailRunner.enviarEmailAtualizacaoProjeto(configuracaoEmail, emailPendente);
                    this.envioEmailService.atualizaSucessoPosEnvioEmail(emailPendente, configuracaoEmail);
                } catch (Exception e) {
                    this.envioEmailService.atualizaErroPosEnvioEmail(emailPendente, configuracaoEmail, e);
                    log.error("Erro ao enviar email ID " + emailPendente.getId());
                }
            });
        } catch (Exception e) {
            log.error("Falha ao realizar envio de emails", e);
        }
    }

}
