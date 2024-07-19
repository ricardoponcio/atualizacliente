package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "projeto_atualizacao_email")
@Data
@Builder
public class ProjetoAtualizacaoEmailEntity {

    public static enum ProjetoAtualizacaoEmailResultado {
        S("Enviado com sucesso"), F("Falha no envio");

        private String descricao;

        ProjetoAtualizacaoEmailResultado(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return this.descricao;
        }

        @Override
        public String toString() {
            return this.getDescricao();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "email_destino")
    private String emailDestino;
    @Column
    private String assunto;
    @Column
    private String corpo;
    @Column(name = "email_solicitado_em")
    private LocalDateTime emailSolicitadoEm;
    @Column(name = "envio_processado_em")
    private LocalDateTime envioProcessadoEm;
    @Column
    @Enumerated(EnumType.STRING)
    private ProjetoAtualizacaoEmailResultado resultado;
    @Column(name = "mensagem_erro")
    private String mensagemErro;
    @Column(name = "projeto_atualizacao_id")
    private Long projetoAtualizacaoId;
    @ManyToOne
    @JoinColumn(name = "projeto_atualizacao_id", insertable = false, updatable = false)
    private ProjetoAtualizacaoEntity projetoAtualizacao;


}
