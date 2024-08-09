package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.AtualizarClienteRequestDTO;
import dev.poncio.atualizacliente.dto.CriarClienteRequestDTO;
import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.repositories.IClienteRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.ClienteMapper;
import dev.poncio.atualizacliente.utils.SpringResourceLoader;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {

    @Value("${frontend.url.base:}")
    private String frontEndUrlBase;

    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EnvioEmailService envioEmailService;

    public ClienteEntity buscarPeloId(Long id) {
        return this.clienteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public boolean clienteEstaValidado(ClienteEntity cliente) {
        return this.clienteRepository.existsByIdAndValidadoTrue(cliente.getId());
    }

    public boolean checarSenhaCliente(String senha, ClienteEntity cliente) {
        ClienteEntity clienteAtualizado = this.buscarPeloId(cliente.getId());
        return this.passwordEncoder.matches(senha, clienteAtualizado.getSenhaVisualizacao());
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
        clienteNovo.setTokenValidacao(UUID.randomUUID().toString());
        ClienteEntity clienteCriado = this.clienteRepository.save(clienteNovo);
        this.emailValidaCliente(clienteCriado);
        return clienteCriado;
    }

    public ClienteEntity validarCliente(String tokenValidacao, String novaSenhaCliente) throws RegraNegocioException {
        Optional<ClienteEntity> clienteSalvoResultado = this.clienteRepository.findByTokenValidacao(tokenValidacao);
        if (clienteSalvoResultado.isEmpty()) {
            throw new RegraNegocioException("Cliente não existe");
        }
        ClienteEntity clienteSalvo = clienteSalvoResultado.get();
        if (clienteSalvo.getValidado() == Boolean.TRUE) {
            throw new RegraNegocioException("Cliente já validado");
        }
        clienteSalvo.setSenhaVisualizacao(this.passwordEncoder.encode(novaSenhaCliente));
        clienteSalvo.setValidado(Boolean.TRUE);
        return this.clienteRepository.save(clienteSalvo);
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

    private EnvioEmailEntity emailValidaCliente(ClienteEntity cliente) {
        String corpo = SpringResourceLoader.getResourceFileAsString("static/email-templates/validar-email-cliente.html")
                .replace("{{url_atualizacao_projeto}}", String.format("%s/validar?__token_validacao_cliente=%s",
                        frontEndUrlBase, cliente.getTokenValidacao()));
        return this.envioEmailService.enviaEmail("Validar Email",
                corpo, cliente.getEmail(), cliente);
    }

    private EnvioEmailEntity emailResetSenhaCliente(ClienteEntity cliente) {
        String corpo = SpringResourceLoader.getResourceFileAsString("static/email-templates/troca-senha-cliente.html")
                .replace("{{url_troca_senha_cliente}}", String.format("%s/troca-senha-cliente?__token_senha_cliente=%s",
                        frontEndUrlBase, null));
        return this.envioEmailService.enviaEmail("Alteração de Senha",
                corpo, cliente.getEmail(), cliente);
    }

}
