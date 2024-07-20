package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.dto.LoginResponseDTO;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    @Autowired
    private ModelMapper modelMapper;

    public LoginResponseDTO map(UsuarioEntity usuario) {
        return this.modelMapper.map(usuario, LoginResponseDTO.class);
    }

    public LoginResponseDTO map(UsuarioEntity usuario, String token) {
        LoginResponseDTO response = this.modelMapper.map(usuario, LoginResponseDTO.class);
        response.setToken(token);
        return response;
    }

}
