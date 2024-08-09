package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.ProjetoAtualizacaoComProjetoDTO;
import dev.poncio.atualizacliente.dto.ProjetoAtualizacaoComProjetoIdDTO;
import dev.poncio.atualizacliente.dto.ProjetoAtualizacaoDTO;
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

    public ProjetoAtualizacaoComProjetoDTO mapDetail(ProjetoAtualizacaoEntity entity) {
        return this.modelMapper.map(entity, ProjetoAtualizacaoComProjetoDTO.class);
    }

    public ProjetoAtualizacaoComProjetoIdDTO mapLessDetail(ProjetoAtualizacaoEntity entity) {
        return this.modelMapper.map(entity, ProjetoAtualizacaoComProjetoIdDTO.class);
    }

}
