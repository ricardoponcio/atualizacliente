package dev.poncio.atualizacliente.controller;

import dev.poncio.atualizacliente.configuration.CustomUserDetails;
import dev.poncio.atualizacliente.dto.LoginRequestDTO;
import dev.poncio.atualizacliente.dto.LoginResponseDTO;
import dev.poncio.atualizacliente.utils.AuthMapper;
import dev.poncio.atualizacliente.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthMapper authMapper;

    @PostMapping("/login")
    public LoginResponseDTO authenticateUser(@Validated @RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return authMapper.map(userDetails.getUsuario(), jwt);
    }

}
