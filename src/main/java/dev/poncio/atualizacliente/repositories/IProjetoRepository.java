package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjetoRepository extends JpaRepository<ProjetoEntity, Long> {

    List<ProjetoEntity> findTop10ByStatusOrderByDataLimiteDesc(ProjetoEntity.ProjetoStatus status);

    Integer countDistinctIdByStatus(ProjetoEntity.ProjetoStatus status);

    Integer countDistinctIdByStatusAndSubStatus(ProjetoEntity.ProjetoStatus status, ProjetoEntity.ProjetoSubStatus subStatus);

}
