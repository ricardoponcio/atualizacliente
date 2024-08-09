package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.entities.ArquivoS3Entity;
import dev.poncio.atualizacliente.repositories.IArquivoS3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArquivoS3Service {

    @Autowired
    private IArquivoS3Repository arquivoS3Repository;

    public ArquivoS3Entity persisteNovaEntrada(ArquivoS3Entity arquivoS3) {
        return this.arquivoS3Repository.save(arquivoS3);
    }

}
