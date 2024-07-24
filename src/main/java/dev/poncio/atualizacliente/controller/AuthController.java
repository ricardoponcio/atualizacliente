package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.configuration.CustomUserDetails;
import dev.poncio.atualizacliente.dto.LoginRequestDTO;
import dev.poncio.atualizacliente.dto.LoginResponseDTO;
import dev.poncio.atualizacliente.utils.AuthMapper;
import dev.poncio.atualizacliente.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${server.servlet.session.cookie.http-only}")
    private Boolean cookieHttpOnly;
    @Value("${server.servlet.session.cookie.secure}")
    private Boolean cookieSecure;
    @Value("${cookie.domain.base-url}")
    private String cookieDomainBaseUrl;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/login")
    public LoginResponseDTO authenticateUser(@Validated @RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        addAuthCookie(response, jwt);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return authMapper.map(userDetails.getUsuario());
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        removeAuthCookie(response);
    }

    private void addAuthCookie(HttpServletResponse response, String jwt) {
        Cookie cookie = new Cookie("ATTCLIENTE_AUTH_ID", jwt);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setSecure(cookieSecure);
        cookie.setHttpOnly(cookieHttpOnly);
        cookie.setPath("/");
        cookie.setDomain(cookieDomainBaseUrl);
        response.addCookie(cookie);
    }

    private void removeAuthCookie(HttpServletResponse response) {
        addAuthCookie(response, null);
    }

}
