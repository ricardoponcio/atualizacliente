package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "projeto_atualizacao_email")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "envio_solicitado_em")
    private LocalDateTime envioSolicitadoEm;
    @Column(name = "envio_processado_em")
    private LocalDateTime envioProcessadoEm;
    @Column
    @Enumerated(EnumType.STRING)
    private ProjetoAtualizacaoEmailResultado resultado;
    @Column(name = "mensagem_erro")
    private String mensagemErro;
    @OneToOne
    @JoinColumn(name = "projeto_atualizacao_id")
    private ProjetoAtualizacaoEntity projetoAtualizacao;

}
