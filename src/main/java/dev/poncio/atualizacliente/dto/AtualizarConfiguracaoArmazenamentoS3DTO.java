package dev.poncio.atualizacliente.dto;

import lombok.Data;

@Data
public class AtualizarConfiguracaoArmazenamentoS3DTO {

    private String s3ServiceEndpoint;
    private String s3Region;
    private String s3BucketName;
    private String prefixoBase;

}
