package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class CriarConfiguracaoArmazenamentoS3DTO {

    private String s3ServiceEndpoint;
    private String s3Region;
    private String s3AccessKey;
    private String s3SecretKey;
    private String s3BucketName;
    private String prefixoBase;

}
