package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConfiguracaoArmazenamentoS3lDTO {

    private Long id;
    private String s3ServiceEndpoint;
    private String s3Region;
    private String s3BucketName;
    private String prefixoBase;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime ultimoUsoSucesso;

}
