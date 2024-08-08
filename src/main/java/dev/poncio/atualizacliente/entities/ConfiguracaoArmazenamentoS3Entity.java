package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "configuracao_armazenamento_S3")
@Data
public class ConfiguracaoArmazenamentoS3Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "s3_service_endpoint")
    private String s3ServiceEndpoint;
    @Column(name = "s3_region")
    private String s3Region;
    @Column(name = "s3_access_key")
    private String s3AccessKey;
    @Column(name = "s3_secret_key")
    private String s3SecretKey;
    @Column(name = "s3_bucket_name")
    private String s3BucketName;
    @Column(name = "prefixo_base")
    private String prefixoBase;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;
    @Column(name = "ultimo_uso_sucesso")
    private LocalDateTime ultimoUsoSucesso;

}
