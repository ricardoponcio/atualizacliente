package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.AtualizarClienteRequestDTO;
import dev.poncio.atualizacliente.dto.ClienteDTO;
import dev.poncio.atualizacliente.dto.CriarClienteRequestDTO;
import dev.poncio.atualizacliente.entities.ClienteEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ClienteDTO map(ClienteEntity entity) {
        return this.modelMapper.map(entity, ClienteDTO.class);
    }

    public ClienteEntity map(CriarClienteRequestDTO criarClienteRequestDTO) {
        return this.modelMapper.map(criarClienteRequestDTO, ClienteEntity.class);
    }

    public ClienteEntity map(AtualizarClienteRequestDTO atualizarClienteRequestDTO) {
        return this.modelMapper.map(atualizarClienteRequestDTO, ClienteEntity.class);
    }

}
