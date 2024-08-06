package dev.poncio.atualizacliente.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginResponseDTO extends UsuarioBasicoDTO {

    private String token;

}
