package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoAtualizacaoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String status;
    private String subStatus;
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;
    private ProjetoAtualizacaoEmailSemProjetoAtualizacaoDTO email;

}
