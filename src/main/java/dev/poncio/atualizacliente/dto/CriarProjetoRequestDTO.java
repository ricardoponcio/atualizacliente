package dev.poncio.atualizacliente.dto;

import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriarProjetoRequestDTO {

    private String nome;
    private String descricao;
    private Double valor;
    private LocalDateTime dataLimite;
    private Long criadoPorId;
    private Long clienteId;

}
