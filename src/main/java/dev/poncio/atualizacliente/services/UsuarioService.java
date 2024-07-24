package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.configuration.CustomUserDetails;
import dev.poncio.atualizacliente.dto.CriaUsuarioRequestDTO;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import dev.poncio.atualizacliente.repositories.IUsuarioRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.UsuarioMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService implements UserDetailsService {

    @Value("${usuarios.permiteCadastro:}")
    private Boolean permiteCadastro;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private AuthContext authContext;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(this.buscarUsuarioPorEmail(username));
    }

    private boolean possuiAlgumUsuarioCadastrado() {
        return !usuarioRepository.findAll().isEmpty();
    }

    private UsuarioEntity buscarUsuarioPorEmail(String email) {
        return this.usuarioRepository.findUsuarioEntityByEmailAndAtivoTrueAndValidadoTrue(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UsuarioEntity cadastraUsuario(CriaUsuarioRequestDTO criaUsuarioRequestDTO) throws Exception {
        boolean isCadastroPermitido = permiteCadastro != null && permiteCadastro;
        if (possuiAlgumUsuarioCadastrado() && !isCadastroPermitido)
            throw new Exception("Não é possível realizar novos cadastros");
        if (buscarUsuarioPorEmail(criaUsuarioRequestDTO.getEmail()) != null)
            throw new Exception("Email já cadastrado");
        UsuarioEntity usuarioNovo = this.usuarioMapper.map(criaUsuarioRequestDTO);
        usuarioNovo.setValidado(false);
        usuarioNovo.setAtivo(true);
        usuarioNovo.setCriadoEm(LocalDateTime.now());
        usuarioNovo.setCriadoPor(authContext.getUsuarioLogado());
        return this.usuarioRepository.save(usuarioNovo);
    }

}
