package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.ProjetoAtualizacaoDTO;
import dev.poncio.atualizacliente.dto.ProjetoAtualizacaoEmailSemProjetoAtualizacaoDTO;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjetoAtualizacaoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProjetoAtualizacaoDTO map(ProjetoAtualizacaoEntity entity) {
        return this.modelMapper.map(entity, ProjetoAtualizacaoDTO.class);
    }

}
