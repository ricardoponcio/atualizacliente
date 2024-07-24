package dev.poncio.atualizacliente.configuration;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class JWTCookieFilter extends AbstractJWTFilter {

    protected String parseJwt(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("ATTCLIENTE_AUTH_ID".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}