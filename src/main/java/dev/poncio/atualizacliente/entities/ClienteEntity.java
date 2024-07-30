package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "razao_social")
    private String razaoSocial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Column
    private String cnpj;
    @Column
    private String email;
    @Column
    private Boolean validado;
    @Column(name = "senha_visualizacao")
    private String senhaVisualizacao;
    @Column(name = "token_validacao")
    private String tokenValidacao;
    @Column
    private Boolean ativo;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @Column(name = "validado_em")
    private LocalDateTime validadoEm;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;

}
