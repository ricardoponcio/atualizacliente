package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoAtualizacaoEmailSimplificadoDTO {

    private Long id;
    private String emailDestino;
    private LocalDateTime envioSolicitadoEm;
    private LocalDateTime envioProcessadoEm;
    private String resultado;
    private String mensagemErro;

}
