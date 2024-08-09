package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.ArquivoS3DTO;
import dev.poncio.atualizacliente.entities.ArquivoS3Entity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArquivoS3Mapper {

    @Autowired
    private ModelMapper modelMapper;

    public ArquivoS3DTO map(ArquivoS3Entity arquivoS3) {
        return this.modelMapper.map(arquivoS3, ArquivoS3DTO.class);
    }

}
