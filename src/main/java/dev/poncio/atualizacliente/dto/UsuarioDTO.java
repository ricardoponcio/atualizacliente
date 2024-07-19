package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDTO extends UsuarioBasicoDTO {

    private LocalDateTime criadoEm;
    private UsuarioDTO criadoPor;

}
