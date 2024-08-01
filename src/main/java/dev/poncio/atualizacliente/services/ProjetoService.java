package dev.poncio.atualizacliente.services;

import dev.poncio.atualizacliente.dto.AtualizaProjetoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoAtualizacaoRequestDTO;
import dev.poncio.atualizacliente.dto.CriarProjetoRequestDTO;
import dev.poncio.atualizacliente.entities.ClienteEntity;
import dev.poncio.atualizacliente.entities.ProjetoAtualizacaoEntity;
import dev.poncio.atualizacliente.entities.ProjetoEntity;
import dev.poncio.atualizacliente.excecoes.RegraNegocioException;
import dev.poncio.atualizacliente.repositories.IProjetoRepository;
import dev.poncio.atualizacliente.utils.AuthContext;
import dev.poncio.atualizacliente.utils.ProjetoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    public ProjetoEntity buscarPeloId(Long id) {
        return this.projetoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ProjetoEntity> listarProjetos() {
        return this.projetoRepository.findAll();
    }

    public ProjetoEntity inserirProjeto(CriarProjetoRequestDTO criarProjetoRequestDTO) {
        ProjetoEntity novoProjeto = projetoMapper.map(criarProjetoRequestDTO);
        novoProjeto.setStatus(ProjetoEntity.ProjetoStatus.A);
        novoProjeto.setSubStatus(ProjetoEntity.ProjetoSubStatus.F);
        novoProjeto.setCriadoEm(LocalDateTime.now());
        novoProjeto.setCriadoPor(authContext.getUsuarioLogado());
        novoProjeto.setCliente(clienteService.buscarPeloId(criarProjetoRequestDTO.getClienteId()));
        return this.projetoRepository.save(novoProjeto);
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

    public List<ProjetoAtualizacaoEntity> listarAtualizacoes(Long projetoId) {
        return this.projetoAtualizacaoService.atualizacaoPorProjeto(projetoId);
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

}
