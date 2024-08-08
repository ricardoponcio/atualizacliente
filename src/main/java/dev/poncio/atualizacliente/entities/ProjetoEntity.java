package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "projeto")
@Data
public class ProjetoEntity {

    public static enum ProjetoStatus {
        ABERTO("Aberto"), CONCLUIDO("Concluído");

        private String descricao;

        ProjetoStatus(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public static ProjetoStatus valueOfDesc(String descricao) {
            return Stream.of(ProjetoStatus.values()).filter(status -> status.getDescricao().equals(descricao)).findFirst().orElse(null);
        }
    }

    public static enum ProjetoSubStatus {
        NA_FILA("Na Fila"), BLOQUEADO("Bloqueado"), EM_ANDAMENTO("Em Andamento"), EM_REVISAO("Em Revisão"), AGUARDANDO_PAGAMENTO("Aguardando Pagamento"), FINALIZADO("Finalizado");

        private String descricao;

        ProjetoSubStatus(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return this.descricao;
        }

        public static ProjetoSubStatus valueOfDesc(String descricao) {
            return Stream.of(ProjetoSubStatus.values()).filter(status -> status.getDescricao().equals(descricao)).findFirst().orElse(null);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column
    private String nome;
    @Column
    private String descricao;
    @Column
    private Double valor;
    @Column(name = "data_limite")
    private LocalDateTime dataLimite;
    @Column
    @Enumerated(EnumType.STRING)
    private ProjetoStatus status;
    @Column(name = "sub_status")
    @Enumerated(EnumType.STRING)
    private ProjetoSubStatus subStatus;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.REMOVE)
    private List<ProjetoAtualizacaoEntity> projetoAtualizacoes;


}
