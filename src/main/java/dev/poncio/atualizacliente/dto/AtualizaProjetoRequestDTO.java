package dev.poncio.atualizacliente.dto;

import dev.poncio.atualizacliente.entities.UsuarioEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtualizaProjetoRequestDTO {

    private String nome;
    private String descricao;
    private Double valor;
    private LocalDateTime dataLimite;

}
