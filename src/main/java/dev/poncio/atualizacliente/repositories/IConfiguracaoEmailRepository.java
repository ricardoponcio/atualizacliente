package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ConfiguracaoEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConfiguracaoEmailRepository extends JpaRepository<ConfiguracaoEmailEntity, Long> {

}
