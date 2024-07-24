package dev.poncio.atualizacliente.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Data
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    @JsonIgnore
    private String senha;
    @Column
    private Boolean validado;
    @Column
    private Boolean ativo;
    @Column
    private LocalDateTime criadoEm;
    @Column
    private LocalDateTime validadoEm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;

}
