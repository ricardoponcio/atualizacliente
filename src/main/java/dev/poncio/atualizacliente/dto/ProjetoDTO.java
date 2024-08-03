package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime dataLimite;
    private String status;
    private String subStatus;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;
    private ClienteSemUsuarioDTO cliente;


}
