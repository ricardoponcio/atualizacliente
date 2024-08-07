package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.SetupDTO;
import dev.poncio.atualizacliente.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setup")
public class SetupController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/check-needed")
    public SetupDTO checaSetupNecessario() {
        return SetupDTO.builder().setupNecessario(!usuarioService.possuiAlgumUsuarioCadastrado()).build();
    }
}
