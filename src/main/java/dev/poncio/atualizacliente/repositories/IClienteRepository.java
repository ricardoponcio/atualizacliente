package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteEntity, Long> {

    boolean existsByIdAndValidadoTrue(Long id);

    Optional<ClienteEntity> findByTokenValidacao(String tokenValidacao);

}
