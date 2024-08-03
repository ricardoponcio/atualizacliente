package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.AtualizarConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.dto.ConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.dto.CriarConfiguracaoEmailDTO;
import dev.poncio.atualizacliente.entities.ConfiguracaoEmailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoEmailMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ConfiguracaoEmailDTO map(ConfiguracaoEmailEntity configuracaoEmail) {
        return this.modelMapper.map(configuracaoEmail, ConfiguracaoEmailDTO.class);
    }

    public ConfiguracaoEmailEntity map(CriarConfiguracaoEmailDTO criarConfiguracaoEmailDTO) {
        return this.modelMapper.map(criarConfiguracaoEmailDTO, ConfiguracaoEmailEntity.class);
    }

    public ConfiguracaoEmailEntity map(AtualizarConfiguracaoEmailDTO atualizarConfiguracaoEmailDTO) {
        return this.modelMapper.map(atualizarConfiguracaoEmailDTO, ConfiguracaoEmailEntity.class);
    }

}
