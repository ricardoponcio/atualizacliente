package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime dataLimite;
    private Long clienteId;

}
