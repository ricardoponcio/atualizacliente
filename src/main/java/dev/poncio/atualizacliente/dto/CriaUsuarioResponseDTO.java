package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class CriaUsuarioResponseDTO {

    private String nome;
    private String email;
    private String senha;

}
