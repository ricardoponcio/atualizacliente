package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.AtualizarConfiguracaoArmazenamentoS3DTO;
import dev.poncio.atualizacliente.dto.ConfiguracaoArmazenamentoS3lDTO;
import dev.poncio.atualizacliente.dto.CriarConfiguracaoArmazenamentoS3DTO;
import dev.poncio.atualizacliente.entities.ConfiguracaoArmazenamentoS3Entity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracaoArmazenamentoS3Mapper {

    @Autowired
    private ModelMapper modelMapper;

    public ConfiguracaoArmazenamentoS3lDTO map(ConfiguracaoArmazenamentoS3Entity configuracaoArmazenamentoS3) {
        return this.modelMapper.map(configuracaoArmazenamentoS3, ConfiguracaoArmazenamentoS3lDTO.class);
    }

    public ConfiguracaoArmazenamentoS3Entity map(CriarConfiguracaoArmazenamentoS3DTO criarConfiguracaoArmazenamentoS3DTO) {
        return this.modelMapper.map(criarConfiguracaoArmazenamentoS3DTO, ConfiguracaoArmazenamentoS3Entity.class);
    }

    public ConfiguracaoArmazenamentoS3Entity map(AtualizarConfiguracaoArmazenamentoS3DTO atualizarConfiguracaoArmazenamentoS3DTO) {
        return this.modelMapper.map(atualizarConfiguracaoArmazenamentoS3DTO, ConfiguracaoArmazenamentoS3Entity.class);
    }

}
