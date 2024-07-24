package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjetoAtualizacaoRepository extends JpaRepository<ProjetoAtualizacaoEntity, Long> {

    List<ProjetoAtualizacaoEntity> findAllByProjetoId(Long projetoId);

}
