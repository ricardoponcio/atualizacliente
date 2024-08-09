package dev.poncio.atualizacliente.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjetoAtualizacaoComProjetoDTO extends ProjetoAtualizacaoDTO {

    private ProjetoDTO projeto;

}
