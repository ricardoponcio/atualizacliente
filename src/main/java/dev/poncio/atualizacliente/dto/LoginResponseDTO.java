package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class LoginResponseDTO extends UsuarioBasicoDTO {

    private String token;

}
