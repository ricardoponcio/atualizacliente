package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findUsuarioEntityByEmailAndAtivoTrueAndValidadoTrue(String email);

}
