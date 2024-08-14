package dev.poncio.atualizacliente.domain;

import dev.poncio.atualizacliente.entities.ProjetoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoFiltro {

    ProjetoEntity.ProjetoStatus status;
    ProjetoEntity.ProjetoSubStatus subStatus;

}
