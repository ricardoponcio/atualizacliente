package dev.poncio.atualizacliente.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import dev.poncio.atualizacliente.entities.ArquivoS3Entity;
import dev.poncio.atualizacliente.entities.ConfiguracaoArmazenamentoS3Entity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.utils.AuthContext;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ArmazenamentoService {

    @Autowired
    private ArquivoS3Service arquivoS3Service;

    @Autowired
    private ConfiguracaoArmazenamentoS3Service configuracaoArmazenamentoS3Service;

    @Autowired
    private AuthContext authContext;

    public ArquivoS3Entity uploadAnexoProjetoAtualizacao(MultipartFile arquivo, ProjetoAtualizacaoEntity atualizacao) {
        try {
            String novoArquivo = String.format("%s.%s", UUID.randomUUID().toString(), FilenameUtils.getExtension(arquivo.getOriginalFilename()));
            String caminhoCompleto = String.format("/projetos/%d/atualizacoes/%d/%s", atualizacao.getProjeto().getId(), atualizacao.getId(), novoArquivo);
            uploadFile(caminhoCompleto, arquivo.getInputStream());
            return arquivoS3Service.persisteNovaEntrada(ArquivoS3Entity.builder()
                    .arquivoNome(arquivo.getOriginalFilename())
                    .arquivoNomeUpload(novoArquivo)
                    .arquivoCaminhoCompleto(buildFullPath(caminhoCompleto))
                    .urlCompleta(buildPublicURL(caminhoCompleto))
                    .configuracaoS3(getConfig())
                    .criadoEm(LocalDateTime.now())
                    .criadoPor(authContext.getUsuarioLogado())
                    .tamanho(arquivo.getSize())
                    .nomeBucket(getConfig().getS3BucketName())
                    .tipo(arquivo.getContentType())
                    .build());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private PutObjectResult uploadFile(String fileNameWithPath, InputStream fileInputStream) {
        ConfiguracaoArmazenamentoS3Entity configS3 = getConfig();
        return get().putObject(new PutObjectRequest(
                configS3.getS3BucketName(),
                buildFullPath(fileNameWithPath),
                fileInputStream,
                null
        ));
    }

    public InputStream downloadFile(ArquivoS3Entity arquivoBaixar) {
        ConfiguracaoArmazenamentoS3Entity configS3 = getConfig();
        S3Object arquivoS3 = get().getObject(
                new GetObjectRequest(configS3.getS3BucketName(), arquivoBaixar.getArquivoCaminhoCompleto()));
        return arquivoS3.getObjectContent();
    }

    private AmazonS3 get() {
        ConfiguracaoArmazenamentoS3Entity configS3 = getConfig();
        AWSCredentials credentials = new BasicAWSCredentials(configS3.getS3AccessKey(), configS3.getS3SecretKey());
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(configS3.getS3Region())
                .build();
    }

    private String buildPublicURL(String fileNameWithPath) {
        ConfiguracaoArmazenamentoS3Entity configS3 = getConfig();
        return String.format("https://%s.s3.%s.%s/%s",
                configS3.getS3BucketName(),
                configS3.getS3Region(),
                getConfig().getS3ServiceEndpoint()
                        .replace("s3.", "")
                        .replace("http://", "")
                        .replace("https://", "")
                        .replace("s3://", ""),
                buildFullPath(fileNameWithPath));
    }

    private String buildFullPath(String fileNameWithPath) {
        ConfiguracaoArmazenamentoS3Entity configS3 = getConfig();
        String caminhoCompleto = new File(configS3.getPrefixoBase(), fileNameWithPath).getPath();
        caminhoCompleto = caminhoCompleto.replaceAll("\\\\", "/");
        if (caminhoCompleto.charAt(0) == '/') {
            caminhoCompleto = caminhoCompleto.substring(1);
        }
        return caminhoCompleto;
    }

    private ConfiguracaoArmazenamentoS3Entity getConfig() {
        return this.configuracaoArmazenamentoS3Service.get();
    }

}
