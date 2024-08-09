package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.AtualizaProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoAtualizacaoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.DownloadArquivoDTO;
import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.entities.EnvioEmailEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.repositories.IProjetoRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.ProjetoMapper;
import dev.poncio.atualizacliente.utils.SpringResourceLoader;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private IProjetoRepository projetoRepository;
    @Autowired
    @Qualifier("partialUpdateMapper")
    private ModelMapper partialUpdateMapper;
    @Autowired
    private ProjetoMapper projetoMapper;
    @Autowired
    private AuthContext authContext;
    @Autowired
    private ProjetoAtualizacaoService projetoAtualizacaoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private EnvioEmailService envioEmailService;

    public ProjetoEntity buscarPeloId(Long id) {
        return this.projetoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ProjetoEntity> listarProjetos() {
        return this.projetoRepository.findAll();
    }

    public ProjetoEntity inserirProjeto(CriarProjetoRequestDTO criarProjetoRequestDTO) {
        ProjetoEntity novoProjeto = projetoMapper.map(criarProjetoRequestDTO);
        novoProjeto.setStatus(ProjetoEntity.ProjetoStatus.ABERTO);
        novoProjeto.setSubStatus(ProjetoEntity.ProjetoSubStatus.NA_FILA);
        novoProjeto.setCriadoEm(LocalDateTime.now());
        novoProjeto.setCriadoPor(authContext.getUsuarioLogado());
        novoProjeto.setCliente(clienteService.buscarPeloId(criarProjetoRequestDTO.getClienteId()));
        ProjetoEntity projetoCriado = this.projetoRepository.save(novoProjeto);
        this.emailNovoProjeto(projetoCriado);
        return projetoCriado;
    }

    public ProjetoEntity atualizarProjeto(Long id, AtualizaProjetoRequestDTO atualizaProjetoRequestDTO) {
        ProjetoEntity projetoSalvo = this.projetoRepository.findById(id).orElse(null);
        if (projetoSalvo == null)
            throw new EntityNotFoundException();

        ProjetoEntity projetoAlteracoes = this.projetoMapper.map(atualizaProjetoRequestDTO);
        partialUpdateMapper.map(projetoAlteracoes, projetoSalvo);
        return this.projetoRepository.save(projetoSalvo);
    }

    public void removerProjeto(Long id) {
        if (!this.projetoRepository.existsById(id))
            throw new EntityNotFoundException();

        this.projetoRepository.deleteById(id);
    }

    public ProjetoAtualizacaoEntity emitirNovaAtualizacao(Long projetoId, CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO) {
        ProjetoEntity projeto = this.buscarPeloId(projetoId);
        return this.projetoAtualizacaoService.inserirAtualizacao(criarProjetoAtualizacaoRequestDTO, projeto, authContext.getUsuarioLogado());
    }

    public ProjetoAtualizacaoEntity emitirNovaAtualizacaoComAnexos(Long projetoId, CriarProjetoAtualizacaoRequestDTO criarProjetoAtualizacaoRequestDTO, List<MultipartFile> anexos) {
        ProjetoEntity projeto = this.buscarPeloId(projetoId);
        return this.projetoAtualizacaoService.inserirAtualizacaoComAnexos(criarProjetoAtualizacaoRequestDTO, projeto, anexos, authContext.getUsuarioLogado());
    }

    public List<ProjetoAtualizacaoEntity> listarAtualizacoes(Long projetoId) {
        return this.projetoAtualizacaoService.atualizacaoPorProjeto(projetoId);
    }

    public ProjetoAtualizacaoEntity detalharAtualizacao(Long projetoAtualizacaoId) {
        return this.projetoAtualizacaoService.buscarPeloIf(projetoAtualizacaoId);
    }

    public ProjetoAtualizacaoEntity retornarAtualizacaoPorToken(String senhaCliente, String token) throws RegraNegocioException {
        ProjetoAtualizacaoEntity projetoAtualizacaoEntity = this.projetoAtualizacaoService.atualizacaoBuscaPorToken(token);
        ClienteEntity cliente = projetoAtualizacaoEntity.getProjeto().getCliente();
        if (!clienteService.clienteEstaValidado(cliente)) {
            throw new RegraNegocioException("Cliente não está validado");
        }
        if (!clienteService.checarSenhaCliente(senhaCliente, cliente)) {
            throw new RegraNegocioException("Senha do cliente é inválida");
        }
        return projetoAtualizacaoEntity;
    }

    public ProjetoEntity atualizaStatusProjeto(ProjetoAtualizacaoEntity projetoAtualizacao) {
        ProjetoEntity projetoSalvo = buscarPeloId(projetoAtualizacao.getProjeto().getId());
        projetoSalvo.setStatus(projetoAtualizacao.getStatus());
        projetoSalvo.setSubStatus(projetoAtualizacao.getSubStatus());
        return this.projetoRepository.save(projetoSalvo);
    }

    public DownloadArquivoDTO baixaAnexoProjetoAtualizacao(Long projetoId, Long projetoAtualizacaoId, String nomeArquivoUpload) {
        ProjetoEntity projetoSalvo = buscarPeloId(projetoId);
        return this.projetoAtualizacaoService.baixarArquivo(projetoSalvo, projetoAtualizacaoId, nomeArquivoUpload);
    }

    public DownloadArquivoDTO baixaAnexoProjetoAtualizacaoToken(Long projetoId, Long projetoAtualizacaoId, String nomeArquivoUpload, String token) {
        ProjetoEntity projetoSalvo = buscarPeloId(projetoId);
        return this.projetoAtualizacaoService.baixarArquivoToken(projetoSalvo, projetoAtualizacaoId, nomeArquivoUpload, token);
    }

    private EnvioEmailEntity emailNovoProjeto(ProjetoEntity projeto) {
        String corpo = SpringResourceLoader.getResourceFileAsString("static/email-templates/criacao-projeto.html")
                .replace("{{nome_do_projeto}}", projeto.getNome());
        return this.envioEmailService.enviaEmail(String.format("Novo Projeto - %s", projeto.getNome()),
                corpo, projeto.getCliente().getEmail(), projeto);
    }

}
