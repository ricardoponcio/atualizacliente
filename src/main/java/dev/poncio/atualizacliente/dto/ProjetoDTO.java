package dev.poncio.atualizacliente.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjetoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Double valor;
    private LocalDateTime dataLimite;
    private String status;
    private String subStatus;
    private LocalDateTime criadoEm;
    private UsuarioBasicoDTO criadoPor;
    private ClienteSemUsuarioDTO cliente;


}
