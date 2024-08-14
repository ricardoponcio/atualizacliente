package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.EnvioEmailCompletoDTO;
import dev.poncio.atualizacliente.dto.EnvioEmailDTO;
import dev.poncio.atualizacliente.dto.EnvioEmailStatusDTO;
import dev.poncio.atualizacliente.services.EnvioEmailService;
import dev.poncio.atualizacliente.utils.EnvioEmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/envio-email")
public class EnvioEmailController {

    @Autowired
    private EnvioEmailMapper envioEmailMapper;

    @Autowired
    private EnvioEmailService envioEmailService;

    @GetMapping("/ultimos")
    public List<EnvioEmailCompletoDTO> ultimosEnvios() {
        return this.envioEmailService.ultimosEmailsProcessados().stream().map(envioEmailMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/status")
    public EnvioEmailStatusDTO status() {
        return this.envioEmailService.statusEnvios();
    }

}
