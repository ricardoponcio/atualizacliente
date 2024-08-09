package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjetoAtualizacaoComProjetoIdDTO extends ProjetoAtualizacaoDTO {

    private ProjetoIdDTO projeto;

}


