package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoAtualizacaoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private String subStatus;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;
    private EnvioEmailSimplificadoDTO email;

}
