package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.dto.AtualizarClienteRequestDTO;
import dev.poncio.atualizacliente.dto.ClienteDTO;
import dev.poncio.atualizacliente.dto.CriarClienteRequestDTO;
import dev.poncio.atualizacliente.entities.ClienteEntity;
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
        ClienteEntity novoCliente = this.clienteService.inserirCliente(clienteMapper.map(criarClienteRequestDTO));
        return clienteMapper.map(novoCliente);
    }

    @PatchMapping("/atualizar/{id}")
    public ClienteDTO atualizarCliente(@PathVariable Long id, @RequestBody AtualizarClienteRequestDTO atualizarClienteRequestDTO) {
        ClienteEntity clienteAtualizado = this.clienteService.atualizarCliente(id, clienteMapper.map(atualizarClienteRequestDTO));
        return clienteMapper.map(clienteAtualizado);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<?> removeCliente(@PathVariable Long id) {
        this.clienteService.removerCliente(id);
        return ResponseEntity.ok().build();
    }

}
