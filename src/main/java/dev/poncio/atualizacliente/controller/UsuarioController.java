package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.CriaUsuarioRequestDTO;
import dev.poncio.atualizacliente.dto.CriaUsuarioResponseDTO;
import dev.poncio.atualizacliente.services.UsuarioService;
import dev.poncio.atualizacliente.utils.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @PutMapping("/cadastro")
    public CriaUsuarioResponseDTO authenticateUser(@Validated @RequestBody CriaUsuarioRequestDTO criaUsuarioRequestDTO) throws Exception {
        return this.usuarioMapper.map(this.usuarioService.cadastraUsuario(criaUsuarioRequestDTO));
    }

}
