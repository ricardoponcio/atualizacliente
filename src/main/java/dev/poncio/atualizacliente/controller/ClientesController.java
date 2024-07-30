package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.AtualizarClienteRequestDTO;
import dev.poncio.atualizacliente.dto.ClienteDTO;
import dev.poncio.atualizacliente.dto.CriarClienteRequestDTO;
import dev.poncio.atualizacliente.dto.ValidarClienteDTO;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.services.ClienteService;
import dev.poncio.atualizacliente.utils.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public List<ClienteDTO> listarClientes() {
        return this.clienteService.listarClientes().stream().map(clienteMapper::map).collect(Collectors.toList());
    }

    @PutMapping("/criar")
    public ClienteDTO criarCliente(@RequestBody CriarClienteRequestDTO criarClienteRequestDTO) {
        return clienteMapper.map(this.clienteService.inserirCliente(criarClienteRequestDTO));
    }

    @PatchMapping("/atualizar/{id}")
    public ClienteDTO atualizarCliente(@PathVariable Long id, @RequestBody AtualizarClienteRequestDTO atualizarClienteRequestDTO) {
        return clienteMapper.map(this.clienteService.atualizarCliente(id, atualizarClienteRequestDTO));
    }

    @PostMapping("/validar/{token}")
    public ClienteDTO validarCliente(@PathVariable String token, @RequestBody ValidarClienteDTO validarClienteDTO) throws RegraNegocioException {
        return clienteMapper.map(this.clienteService.validarCliente(token, validarClienteDTO.getSenhaCliente()));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removeCliente(@PathVariable Long id) {
        this.clienteService.removerCliente(id);
        return ResponseEntity.ok().build();
    }

}
