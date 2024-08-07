package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnvioEmailCompletoDTO {

    private String emailDestino;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime envioSolicitadoEm;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime envioProcessadoEm;
    private String resultado;
    private String mensagemErro;
    private String smtpHost;
    private Long smtpPort;
    private Boolean smtpSsl;
    private Boolean smtpTls;
    private Boolean smtpAuth;
    private String tipo;

}
