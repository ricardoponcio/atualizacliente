package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtualizaProjetoRequestDTO {

    private String nome;
    private String descricao;
    private Double valor;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime dataLimite;

}
