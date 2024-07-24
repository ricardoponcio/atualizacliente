package dev.poncio.atualizacliente.utils;

import dev.poncio.atualizacliente.configuration.CustomUserDetails;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthContext {

    public UsuarioEntity getUsuarioLogado() {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao == null || !(autenticacao instanceof UsernamePasswordAuthenticationToken)) return null;
        UsernamePasswordAuthenticationToken usuarioAutenticadoToken = (UsernamePasswordAuthenticationToken) autenticacao;
        Object usuarioAutenticadoTokenPrincipal = usuarioAutenticadoToken.getPrincipal();
        if (usuarioAutenticadoTokenPrincipal == null || !(usuarioAutenticadoTokenPrincipal instanceof CustomUserDetails))
            return null;
        CustomUserDetails usuarioLogado = (CustomUserDetails) usuarioAutenticadoTokenPrincipal;
        if (usuarioLogado == null) return null;
        return usuarioLogado.getUsuario();
    }

    public Long getUsuarioLogadoId() {
        UsuarioEntity usuarioLogado = getUsuarioLogado();
        if (usuarioLogado == null) return null;
        return usuarioLogado.getId();
    }

}
