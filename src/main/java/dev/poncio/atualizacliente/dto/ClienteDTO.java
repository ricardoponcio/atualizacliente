package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class ClienteDTO extends ClienteSemUsuarioDTO {

    private UsuarioBasicoDTO criadoPor;

}
