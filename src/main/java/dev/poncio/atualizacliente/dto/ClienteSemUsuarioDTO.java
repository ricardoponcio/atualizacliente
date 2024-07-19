package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteSemUsuarioDTO {

    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String email;
    private LocalDateTime criadoEm;

}
