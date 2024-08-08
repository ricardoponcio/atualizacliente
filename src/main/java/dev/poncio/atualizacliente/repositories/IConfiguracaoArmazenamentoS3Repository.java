package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ConfiguracaoArmazenamentoS3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConfiguracaoArmazenamentoS3Repository extends JpaRepository<ConfiguracaoArmazenamentoS3Entity, Long> {

}
