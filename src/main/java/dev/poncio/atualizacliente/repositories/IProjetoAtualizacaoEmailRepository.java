package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjetoAtualizacaoEmailRepository extends JpaRepository<ProjetoAtualizacaoEmailEntity, Long> {
}
