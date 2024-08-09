package dev.poncio.atualizacliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadArquivoDTO {

    private InputStream inputStream;
    private String contentType;
    private String nomeArquivo;

}
