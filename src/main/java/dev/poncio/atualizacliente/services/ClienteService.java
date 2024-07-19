package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.repositories.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;

    @Autowired
    private IClienteRepository clienteRepository;

    public List<ClienteEntity> listarClientes() {
        return this.clienteRepository.findAll();
    }

    public ClienteEntity inserirCliente(ClienteEntity cliente) {
        cliente.setCriadoEm(LocalDateTime.now());
        cliente.setValidado(false);
        cliente.setAtivo(true);
        return this.clienteRepository.save(cliente);
    }

    public ClienteEntity atualizarCliente(Long id, ClienteEntity cliente) {
        ClienteEntity clienteSalvo = this.clienteRepository.findById(id).orElse(null);
        if (clienteSalvo == null)
            throw new EntityNotFoundException();

        partialUpdateMapper.map(cliente, clienteSalvo);
        return this.clienteRepository.save(clienteSalvo);
    }

    public void removerCliente(Long id) {
        if (!this.clienteRepository.existsById(id))
            throw new EntityNotFoundException();

        this.clienteRepository.deleteById(id);
    }

}
