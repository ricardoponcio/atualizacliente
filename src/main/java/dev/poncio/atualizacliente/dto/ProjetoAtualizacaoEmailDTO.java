package dev.poncio.atualizacliente.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjetoAtualizacaoEmailDTO extends ProjetoAtualizacaoEmailSimplificadoDTO {

    private String assunto;
    private String corpo;
    private ProjetoAtualizacaoEmailDTO projetoAtualizacao;

}
