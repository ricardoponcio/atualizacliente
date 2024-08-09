package dev.poncio.atualizacliente.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnvioEmailDTO extends EnvioEmailSimplificadoDTO {

    private String assunto;
    private String corpo;
    private String enviadoDe;
    private ProjetoAtualizacaoDTO projetoAtualizacao;
    private ClienteSemUsuarioDTO cliente;
    private UsuarioBasicoDTO usuario;

}
