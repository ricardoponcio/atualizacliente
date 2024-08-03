package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class CriarConfiguracaoEmailDTO {

    private String smtpHost;
    private Long smtpPort;
    private Boolean smtpSsl;
    private Boolean smtpAuth;
    private String smtpHUser;
    private String smtpPassword;

}
