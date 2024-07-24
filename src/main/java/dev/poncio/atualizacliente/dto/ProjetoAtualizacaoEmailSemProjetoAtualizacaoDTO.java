package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoAtualizacaoEmailSemProjetoAtualizacaoDTO {

    private Long id;
    private String emailDestino;
    private String assunto;
    private String corpo;
    private LocalDateTime envioSolicitadoEm;
    private LocalDateTime envioProcessadoEm;
    private String resultado;
    private String mensagemErro;

}
