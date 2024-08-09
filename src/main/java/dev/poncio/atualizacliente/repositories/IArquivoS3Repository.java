package dev.poncio.atualizacliente.repositories;

import dev.poncio.atualizacliente.entities.ArquivoS3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArquivoS3Repository extends JpaRepository<ArquivoS3Entity, Long> {

}
