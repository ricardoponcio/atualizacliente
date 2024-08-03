package dev.poncio.atualizacliente.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteSemUsuarioDTO {

    private Long id;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss-03:00")
    private LocalDateTime criadoEm;

}
