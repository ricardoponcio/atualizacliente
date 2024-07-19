package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioBasicoDTO {

    private Long id;
    private String nome;
    private String email;

}
