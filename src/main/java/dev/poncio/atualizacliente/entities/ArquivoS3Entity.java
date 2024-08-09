package dev.poncio.atualizacliente.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "arquivo_s3")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArquivoS3Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(name = "arquivo_nome")
    private String arquivoNome;
    @Column(name = "arquivo_nome_upload")
    private String arquivoNomeUpload;
    @Column(name = "arquivo_caminho_completo")
    private String arquivoCaminhoCompleto;
    @Column(name = "nome_bucket")
    private String nomeBucket;
    @Column(name = "tamanho")
    private Long tamanho;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "url_completa")
    private String urlCompleta;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private UsuarioEntity criadoPor;
    @ManyToOne
    @JoinColumn(name = "configuracao_s3_id")
    private ConfiguracaoArmazenamentoS3Entity configuracaoS3;

}
