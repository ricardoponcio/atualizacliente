package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoAtualizacaoEmailDTO {

    private Long id;
    private String emailDestino;
    private String assunto;
    private String corpo;
    private LocalDateTime emailSolicitadoEm;
    private LocalDateTime envioProcessadoEm;
    private String resultado;
    private String mensagemErro;
    private ProjetoAtualizacaoEmailDTO projetoAtualizacao;

}
