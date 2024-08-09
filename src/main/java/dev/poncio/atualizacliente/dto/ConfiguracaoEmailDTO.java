package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfiguracaoEmailDTO {

    private Long id;
    private String smtpHost;
    private Long smtpPort;
    private Boolean smtpSsl;
    private Boolean smtpTls;
    private Boolean smtpAuth;
    private String enviarDe;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime ultimoUsoSucesso;

}
