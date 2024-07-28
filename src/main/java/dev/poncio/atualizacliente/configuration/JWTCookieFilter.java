package dev.poncio.atualizacliente.configuration;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class JWTCookieFilter extends AbstractJWTFilter {

    protected String parseJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ATTCLIENTE_AUTH_ID".equals(cookie.getName())) {
                    if (cookie.getValue() != null && !cookie.getValue().isEmpty()) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

}