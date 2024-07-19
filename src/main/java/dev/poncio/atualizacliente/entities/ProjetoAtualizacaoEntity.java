package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "projeto_atualizacao")
@Data
public class ProjetoAtualizacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column
    private String titulo;
    @Column
    private String descricao;
    @Column
    @Enumerated(EnumType.STRING)
    private ProjetoEntity.ProjetoStatus status;
    @Column(name = "sub_status")
    @Enumerated(EnumType.STRING)
    private ProjetoEntity.ProjetoSubStatus subStatus;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @Column(name = "criado_por_id")
    private Long criadoPorId;
    @ManyToOne
    @JoinColumn(name = "criado_por_id", insertable = false, updatable = false)
    private UsuarioEntity criadoPor;
    @Column(name = "projeto_id")
    private Long projetoId;
    @ManyToOne
    @JoinColumn(name = "projeto_id", insertable = false, updatable = false)
    private ProjetoEntity projeto;


}
