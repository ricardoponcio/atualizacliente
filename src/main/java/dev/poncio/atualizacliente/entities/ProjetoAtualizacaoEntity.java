package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "projeto_atualizacao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "token_view")
    private String tokenView;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;
    @ManyToOne
    @JoinColumn(name = "projeto_id")
    private ProjetoEntity projeto;
    @OneToOne(mappedBy = "projetoAtualizacao", cascade = CascadeType.REMOVE)
    private EnvioEmailEntity email;

    @ManyToMany
    @JoinTable(
            name = "projeto_atualizacao_anexo",
            joinColumns = @JoinColumn(name = "projeto_atualizacao_id"),
            inverseJoinColumns = @JoinColumn(name = "arquivo_s3_id"))
    private List<ArquivoS3Entity> anexos;

}
