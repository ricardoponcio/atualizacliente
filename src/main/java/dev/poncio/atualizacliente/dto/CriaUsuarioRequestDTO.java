package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class CriaUsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;

}
