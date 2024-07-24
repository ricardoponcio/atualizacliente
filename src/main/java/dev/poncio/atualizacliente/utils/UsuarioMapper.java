package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.CriaUsuarioRequestDTO;
import dev.poncio.atualizacliente.dto.CriaUsuarioResponseDTO;
import dev.poncio.atualizacliente.dto.LoginResponseDTO;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioEntity map(CriaUsuarioRequestDTO criaUsuarioRequestDTO) {
        return this.modelMapper.map(criaUsuarioRequestDTO, UsuarioEntity.class);
    }

    public CriaUsuarioResponseDTO map(UsuarioEntity usuario) {
        return this.modelMapper.map(usuario, CriaUsuarioResponseDTO.class);
    }

}
