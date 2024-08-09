package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "configuracao_email")
public class ConfiguracaoEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
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
    @Column(name = "smtp_user")
    private String smtpUser;
    @Column(name = "smtp_password")
    private String smtpPassword;
    @Column(name = "enviar_de")
    private String enviarDe;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;
    @Column(name = "ultimo_uso_sucesso")
    private LocalDateTime ultimoUsoSucesso;

}
