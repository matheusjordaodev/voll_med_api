package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controlador REST para gerenciamento de médicos.
 * Fornece endpoints para cadastro, listagem, atualização, exclusão e detalhamento.
 */
@RestController
@RequestMapping("medicos")  // Base path para todos os endpoints: /medicos
public class MedicoController {

    @Autowired
    private MedicoRepository repository;  // Repositório JPA para operações com Médicos

    /**
     * Endpoint para cadastro de um novo médico.
     * URL: POST /medicos
     * - @RequestBody desserializa JSON em DadosCadastroMedico e @Valid aplica validações.
     * - @Transactional garante commit no fim do método.
     * - UriComponentsBuilder gera a URI do recurso criado.
     */
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(
            @RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder
    ) {
        // Cria a entidade Medico a partir dos dados recebidos
        var medico = new Medico(dados);
        // Persiste no banco
        repository.save(medico);

        // Constrói URI para o recurso criado: /medicos/{id}
        var uri = uriBuilder
                .path("/medicos/{id}")
                .buildAndExpand(medico.getId())
                .toUri();

        // Retorna status 201 Created, cabeçalho Location e corpo com detalhes
        return ResponseEntity.created(uri)
                .body(new DadosDetalhamentoMedico(medico));
    }

    /**
     * Endpoint para listagem paginada de médicos ativos.
     * URL: GET /medicos
     * - @PageableDefault define tamanho e ordenação padrão (10 por página, ordenado por nome).
     * - Retorna 200 OK com uma página de DadosListagemMedico.
     */
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao
    ) {
        // Busca médicos com ativo=true e converte cada Medico para DadosListagemMedico
        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    /**
     * Endpoint para atualização de dados de um médico existente.
     * URL: PUT /medicos
     * - @RequestBody desserializa JSON em DadosAtualizacaoMedico e @Valid aplica validações.
     * - @Transactional garante commit no fim do método.
     * - Retorna 200 OK com detalhes atualizados.
     */
    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(
            @RequestBody @Valid DadosAtualizacaoMedico dados
    ) {
        // Obtém referência "lazy" ao médico pelo ID (sem consulta imediata)
        var medico = repository.getReferenceById(dados.id());
        // Aplica mudanças somente nos campos não nulos
        medico.atualizarInformacoes(dados);
        // Retorna detalhes atualizados
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    /**
     * Endpoint para exclusão lógica de um médico.
     * URL: DELETE /medicos/{id}
     * - @PathVariable captura o ID na URL.
     * - @Transactional garante commit no fim do método.
     * - Retorna 204 No Content.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);  // Obtém referência ao médico
        medico.excluir();                              // Marca como inativo (exclusão lógica)
        return ResponseEntity.noContent().build();     // Retorna status 204
    }

    /**
     * Endpoint para obter detalhes de um médico específico.
     * URL: GET /medicos/{id}
     * - @PathVariable captura o ID na URL.
     * - Retorna 200 OK com DadosDetalhamentoMedico.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);  // Obtém referência ao médico
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
