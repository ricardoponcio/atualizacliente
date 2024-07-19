package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.AtualizaProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.ProjetoDTO;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjetoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProjetoDTO map(ProjetoEntity entity) {
        return this.modelMapper.map(entity, ProjetoDTO.class);
    }

    public ProjetoEntity map(CriarProjetoRequestDTO criarProjetoRequestDTO) {
        return this.modelMapper.map(criarProjetoRequestDTO, ProjetoEntity.class);
    }

    public ProjetoEntity map(AtualizaProjetoRequestDTO atualizaProjetoRequestDTO) {
        return this.modelMapper.map(atualizaProjetoRequestDTO, ProjetoEntity.class);
    }

}
