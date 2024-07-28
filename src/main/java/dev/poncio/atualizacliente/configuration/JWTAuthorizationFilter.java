package dev.poncio.atualizacliente.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class JWTAuthorizationFilter extends AbstractJWTFilter {

    protected String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            if (headerAuth.length() > 8) {
                String bearerToken = headerAuth.substring(7);
                if (!bearerToken.trim().isEmpty()) {
                    return bearerToken;
                }
            }
        }

        return null;
    }

}