package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class AtualizarClienteRequestDTO {

    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String email;

}
