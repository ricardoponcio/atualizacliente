package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.configuration.CustomUserDetails;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import dev.poncio.atualizacliente.repositories.IUsuarioRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
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

    public UsuarioEntity cadastraUsuario(UsuarioEntity usuario) throws Exception {
        if (permiteCadastro == null || !permiteCadastro.booleanValue())
            throw new Exception("Não é possível realizar novos cadastros");
        usuario.setValidado(false);
        usuario.setAtivo(true);
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setCriadoPor(authContext.getUsuarioLogado());
        return this.usuarioRepository.save(usuario);
    }

}
