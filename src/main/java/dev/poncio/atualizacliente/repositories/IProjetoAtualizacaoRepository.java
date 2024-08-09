package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProjetoAtualizacaoRepository extends JpaRepository<ProjetoAtualizacaoEntity, Long> {

    List<ProjetoAtualizacaoEntity> findAllByProjetoId(Long projetoId);

    Optional<ProjetoAtualizacaoEntity> findByTokenView(String tokenView);

    Optional<ProjetoAtualizacaoEntity> findByIdAndProjetoId(Long id, Long projetoId);

    Optional<ProjetoAtualizacaoEntity> findByIdAndProjetoIdAndTokenView(Long id, Long projetoId, String tokenView);

}
