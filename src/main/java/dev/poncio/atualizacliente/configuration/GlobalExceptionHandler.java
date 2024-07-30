package dev.poncio.atualizacliente.configuration;

import dev.poncio.atualizacliente.dto.ExcecaoDTO;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Object> regraNegocioExceptionHandler(RegraNegocioException exception, WebRequest request) {
        return handleExceptionInternal(exception, ExcecaoDTO.builder().mensagem(exception.getMessage()).build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}