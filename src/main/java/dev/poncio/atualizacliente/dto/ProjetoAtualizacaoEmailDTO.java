package dev.poncio.atualizacliente.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjetoAtualizacaoEmailDTO extends ProjetoAtualizacaoEmailSemProjetoAtualizacaoDTO {

    private ProjetoAtualizacaoEmailDTO projetoAtualizacao;

}
