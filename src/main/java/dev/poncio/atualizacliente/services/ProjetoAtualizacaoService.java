package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.CriarProjetoAtualizacaoRequestDTO;
import dev.poncio.atualizacliente.dto.DownloadArquivoDTO;
import dev.poncio.atualizacliente.entities.ArquivoS3Entity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.entities.UsuarioEntity;
import dev.poncio.atualizacliente.repositories.IProjetoAtualizacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjetoAtualizacaoService {

    @Autowired
    private IProjetoAtualizacaoRepository projetoAtualizacaoRepository;

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private ArmazenamentoService armazenamentoService;

    public ProjetoAtualizacaoEntity buscarPeloIf(Long projetoAtualizacaoId) {
        return this.projetoAtualizacaoRepository.findById(projetoAtualizacaoId).orElseThrow(EntityNotFoundException::new);
    }

    public List<ProjetoAtualizacaoEntity> atualizacaoPorProjeto(Long projetoId) {
        return this.projetoAtualizacaoRepository.findAllByProjetoId(projetoId);
    }

    public ProjetoAtualizacaoEntity atualizacaoBuscaPorToken(String token) {
        return this.projetoAtualizacaoRepository.findByTokenView(token).orElseThrow(EntityNotFoundException::new);
    }

    public ProjetoAtualizacaoEntity inserirAtualizacaoComAnexos(CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO, ProjetoEntity projeto, List<MultipartFile> anexos, UsuarioEntity usuarioLogado) {
        ProjetoAtualizacaoEntity atualizacao = inserirAtualizacao(criarProjetoAtualizacaoRequestDTO, projeto, usuarioLogado);

        List<ArquivoS3Entity> anexosSalvos = new ArrayList<>();
        for (MultipartFile anexo : anexos) {
            ArquivoS3Entity anexoSalvo = this.armazenamentoService.uploadAnexoProjetoAtualizacao(anexo, atualizacao);
            anexosSalvos.add(anexoSalvo);
        }

        atualizacao.setAnexos(anexosSalvos);
        return this.projetoAtualizacaoRepository.save(atualizacao);
    }

    public ProjetoAtualizacaoEntity inserirAtualizacao(CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO, ProjetoEntity projeto, UsuarioEntity usuarioLogado) {
        return inserirAtualizacao(ProjetoAtualizacaoEntity.builder()
                .projeto(projeto)
                .criadoEm(LocalDateTime.now())
                .criadoPor(usuarioLogado)
                .titulo(projeto.getNome())
                .descricao(criarProjetoAtualizacaoRequestDTO.getDescricao())
                .status(ProjetoEntity.ProjetoStatus.valueOfDesc(criarProjetoAtualizacaoRequestDTO.getStatus()))
                .subStatus(ProjetoEntity.ProjetoSubStatus.valueOfDesc(criarProjetoAtualizacaoRequestDTO.getSubStatus()))
                .tokenView(UUID.randomUUID().toString()).build());
    }

    private ProjetoAtualizacaoEntity inserirAtualizacao(ProjetoAtualizacaoEntity projetoAtualizacao) {
        ProjetoAtualizacaoEntity projetoAtualizacaoCriado = this.projetoAtualizacaoRepository.save(projetoAtualizacao);
        this.projetoService.atualizaStatusProjeto(projetoAtualizacaoCriado);
        this.envioEmailService.registraIntencaoEmail(projetoAtualizacaoCriado);
        return projetoAtualizacaoCriado;
    }

    public DownloadArquivoDTO baixarArquivo(ProjetoEntity projetoEntity, Long projetoAtualizacaoId, String nomeArquivoUpload) {
        ProjetoAtualizacaoEntity projetoAtualizacao = this.projetoAtualizacaoRepository
                .findByIdAndProjetoId(projetoAtualizacaoId, projetoEntity.getId()).orElseThrow(EntityNotFoundException::new);
        return baixarAnexo(projetoAtualizacao, nomeArquivoUpload);
    }

    public DownloadArquivoDTO baixarArquivoToken(ProjetoEntity projetoEntity, Long projetoAtualizacaoId, String nomeArquivoUpload, String token) {
        ProjetoAtualizacaoEntity projetoAtualizacao = this.projetoAtualizacaoRepository
                .findByIdAndProjetoIdAndTokenView(projetoAtualizacaoId, projetoEntity.getId(), token).orElseThrow(EntityNotFoundException::new);
        return baixarAnexo(projetoAtualizacao, nomeArquivoUpload);
    }

    private DownloadArquivoDTO baixarAnexo(ProjetoAtualizacaoEntity projetoAtualizacao, String nomeArquivoUpload) {
        ArquivoS3Entity arquivoS3 = projetoAtualizacao.getAnexos().stream()
                .filter(anexo -> anexo.getArquivoNomeUpload().equals(nomeArquivoUpload)).findFirst().orElse(null);

        InputStream inputStream = this.armazenamentoService.downloadFile(arquivoS3);
        return DownloadArquivoDTO.builder().inputStream(inputStream).contentType(arquivoS3.getTipo()).nomeArquivo(arquivoS3.getArquivoNome()).build();
    }

}
