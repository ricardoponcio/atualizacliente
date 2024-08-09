package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.poncio.atualizacliente.entities.ConfiguracaoArmazenamentoS3Entity;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class ArquivoS3DTO {

    private Long id;
    private String arquivoNome;
    private String arquivoNomeUpload;
    private String arquivoCaminhoCompleto;
    private String nomeBucket;
    private BigInteger tamanho;
    private String tipo;
    private String urlCompleta;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;

}
