package dev.poncio.atualizacliente.dto;

import dev.poncio.atualizacliente.entities.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarClienteRequestDTO {

    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String email;

}
