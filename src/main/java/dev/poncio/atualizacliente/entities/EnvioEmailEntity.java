package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "envio_email")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioEmailEntity {

    public static enum EnvioEmailResultado {
        ENVIADO_SUCESSO("Enviado com sucesso"), ENVIO_FALHOU("Falha no envio");

        private String descricao;

        EnvioEmailResultado(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return this.descricao;
        }

    }

    public static enum EnvioEmailTipo {
        CLIENTE_VALIDACAO("Validação Cliente"),
        CLIENTE_RESET_SENHA("Troca de senha Cliente"),
        USUARIO_VALIDACAO("Validação Usuário"),
        USUARIO_RESET_SENHA("Troca de senha Usuário"),
        PROJETO_ATUALIZACAO("Atualização de Projeto"),
        PROJETO_CRIACAO("Criação de Projeto");

        private String descricao;

        EnvioEmailTipo(String descricao) {
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
    private EnvioEmailResultado resultado;
    @Column(name = "mensagem_erro")
    private String mensagemErro;
    @Column(name = "smtp_host")
    private String smtpHost;
    @Column(name = "smtp_port")
    private Long smtpPort;
    @Column(name = "smtp_ssl")
    private Boolean smtpSsl;
    @Column(name = "smtp_tls")
    private Boolean smtpTls;
    @Column(name = "smtp_auth")
    private Boolean smtpAuth;
    @Column(name = "enviado_de")
    private String enviadoDe;
    @Column(name = "tipo_email")
    @Enumerated(EnumType.STRING)
    private EnvioEmailTipo tipo;
    @OneToOne
    @JoinColumn(name = "projeto_atualizacao_id")
    private ProjetoAtualizacaoEntity projetoAtualizacao;
    @OneToOne
    @JoinColumn(name = "projeto_id")
    private ProjetoEntity projeto;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;
    @OneToOne
    @JoinColumn(name = "configuracao_email_id")
    private ConfiguracaoEmailEntity configuracaoEmail;

}
