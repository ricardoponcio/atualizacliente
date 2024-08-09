package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class AtualizarConfiguracaoEmailDTO {

    private String smtpHost;
    private Long smtpPort;
    private Boolean smtpSsl;
    private Boolean smtpTls;
    private Boolean smtpAuth;
    private String enviarDe;

}
