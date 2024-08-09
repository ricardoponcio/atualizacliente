package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.configuration.CustomUserDetails;
import dev.poncio.atualizacliente.dto.CriaUsuarioRequestDTO;
import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import dev.poncio.atualizacliente.repositories.IUsuarioRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.SpringResourceLoader;
import dev.poncio.atualizacliente.utils.UsuarioMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService implements UserDetailsService {

    @Value("${frontend.url.base:}")
    private String frontEndUrlBase;

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
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private EnvioEmailService envioEmailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(this.buscarUsuarioPorEmail(username));
    }

    public boolean possuiAlgumUsuarioCadastrado() {
        return !usuarioRepository.findAll().isEmpty();
    }

    private UsuarioEntity buscarUsuarioPorEmail(String email) {
        return this.usuarioRepository.findUsuarioEntityByEmailAndAtivoTrueAndValidadoTrue(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public UsuarioEntity cadastraUsuario(CriaUsuarioRequestDTO criaUsuarioRequestDTO) throws Exception {
        boolean isCadastroPermitido = permiteCadastro != null && permiteCadastro;
        boolean possuiUsuarioCadastrado = possuiAlgumUsuarioCadastrado();
        if (possuiUsuarioCadastrado && !isCadastroPermitido)
            throw new Exception("Não é possível realizar novos cadastros");
        try {
            buscarUsuarioPorEmail(criaUsuarioRequestDTO.getEmail());
            throw new Exception("Email já cadastrado");
        } catch (Exception e) {
            // Usuário não existe
        }
        UsuarioEntity usuarioNovo = this.usuarioMapper.map(criaUsuarioRequestDTO);
        usuarioNovo.setValidado(false);
        usuarioNovo.setAtivo(true);
        usuarioNovo.setCriadoEm(LocalDateTime.now());
        usuarioNovo.setCriadoPor(authContext.getUsuarioLogado());
        usuarioNovo.setSenha(this.passwordEncoder.encode(usuarioNovo.getSenha()));
        usuarioNovo.setValidado(!possuiUsuarioCadastrado);
        usuarioNovo.setValidadoEm(LocalDateTime.now());
        UsuarioEntity usuarioCriado = this.usuarioRepository.save(usuarioNovo);
        if (!possuiUsuarioCadastrado) {
            this.emailValidaUsuario(usuarioCriado);
        }
        return usuarioCriado;
    }

    private EnvioEmailEntity emailValidaUsuario(UsuarioEntity usuario) {
        String corpo = SpringResourceLoader.getResourceFileAsString("static/email-templates/validar-email-usuario.html")
                .replace("{{url_validacao_usuario}}", String.format("%s/validar-usuario?__token_validacao_usuario=%s",
                        frontEndUrlBase, null));
        return this.envioEmailService.enviaEmail("Validar Email",
                corpo, usuario.getEmail(), usuario);
    }

    private EnvioEmailEntity emailResetSenhaUsuario(UsuarioEntity usuario) {
        String corpo = SpringResourceLoader.getResourceFileAsString("static/email-templates/troca-senha-usuario.html")
                .replace("{{url_troca_senha_usuario}}", String.format("%s/troca-senha-usuario?__token_senha_usuario=%s",
                        frontEndUrlBase, null));
        return this.envioEmailService.enviaEmail("Alteração de Senha",
                corpo, usuario.getEmail(), usuario);
    }

}
