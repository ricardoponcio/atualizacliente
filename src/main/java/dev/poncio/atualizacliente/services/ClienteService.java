package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.AtualizarClienteRequestDTO;
import dev.poncio.atualizacliente.dto.CriarClienteRequestDTO;
import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.repositories.IClienteRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.ClienteMapper;
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
    private IClienteRepository clienteRepository;
    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private AuthContext authContext;

    public ClienteEntity buscarPeloId(Long id) {
        return this.clienteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ClienteEntity> listarClientes() {
        return this.clienteRepository.findAll();
    }

    public ClienteEntity inserirCliente(CriarClienteRequestDTO criarClienteRequestDTO) {
        ClienteEntity clienteNovo = clienteMapper.map(criarClienteRequestDTO);
        clienteNovo.setCriadoEm(LocalDateTime.now());
        clienteNovo.setValidado(false);
        clienteNovo.setAtivo(true);
        clienteNovo.setCriadoPor(authContext.getUsuarioLogado());
        return this.clienteRepository.save(clienteNovo);
    }

    public ClienteEntity atualizarCliente(Long id, AtualizarClienteRequestDTO atualizarClienteRequestDTO) {
        ClienteEntity clienteSalvo = this.clienteRepository.findById(id).orElse(null);
        if (clienteSalvo == null)
            throw new EntityNotFoundException();

        ClienteEntity clienteAlteracoes = clienteMapper.map(atualizarClienteRequestDTO);
        partialUpdateMapper.map(clienteAlteracoes, clienteSalvo);
        return this.clienteRepository.save(clienteSalvo);
    }

    public void removerCliente(Long id) {
        if (!this.clienteRepository.existsById(id))
            throw new EntityNotFoundException();

        this.clienteRepository.deleteById(id);
    }

}
