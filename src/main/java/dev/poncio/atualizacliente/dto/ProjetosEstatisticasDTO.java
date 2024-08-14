package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjetosEstatisticasDTO {

    private Integer projetosAbertos;
    private Integer projetosAguardandoPagamento;
    private List<ProjetoDTO> proximosProjetosVencer;

}
