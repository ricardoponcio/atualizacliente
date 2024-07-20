package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String senha;

}
