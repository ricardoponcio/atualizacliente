package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEnvioEmailRepository extends JpaRepository<EnvioEmailEntity, Long> {

    List<EnvioEmailEntity> findAllByEnvioProcessadoEmIsNull();

    List<EnvioEmailEntity> findTop10ByEnvioProcessadoEmIsNotNullOrderByIdDesc();

    Optional<EnvioEmailEntity> findTopByResultadoAndEnvioProcessadoEmIsNotNullOrderByIdDesc(EnvioEmailEntity.EnvioEmailResultado resultado);

    Optional<EnvioEmailEntity> findTopByEnvioProcessadoEmIsNotNullOrderByIdDesc();

}
