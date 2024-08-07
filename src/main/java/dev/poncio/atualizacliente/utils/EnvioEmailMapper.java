package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.EnvioEmailCompletoDTO;
import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvioEmailMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EnvioEmailCompletoDTO map(EnvioEmailEntity envioEmail) {
        return this.modelMapper.map(envioEmail, EnvioEmailCompletoDTO.class);
    }

}
