package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjetoRepository extends JpaRepository<ProjetoEntity, Long> {
}
