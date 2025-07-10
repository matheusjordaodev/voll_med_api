package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expõe endpoints para operações CRUD em Pacientes.
 * Cada método corresponde a uma rota HTTP e lida com validação,
 * persistência, atualização e exclusão lógica de entidades Paciente.
 */
@RestController                            // Marca esta classe como controlador REST
@RequestMapping("pacientes")             // Base path para todos os endpoints: /pacientes
public class PacienteController {

    /**
     * Repositório JPA para acesso a dados de Paciente.
     * Injetado automaticamente pelo Spring.
     */
    @Autowired
    private PacienteRepository repository;

    /**
     * Cria um novo paciente.
     *
     * Endpoint: POST /pacientes
     * - @RequestBody: desserializa o JSON recebido em DadosCadastroPaciente
     * - @Valid: dispara validação Bean Validation nos campos do DTO
     * - @Transactional: abre transação, garante commit automático
     *
     * Exemplo de requisição JSON:
     * {
     *   "nome": "João",
     *   "email": "joao@exemplo.com",
     *   "telefone": "21999999999",
     *   "cpf": "123.456.789-00",
     *   "endereco": { ... }
     * }
     */
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroPaciente dados) {
        // Constrói a entidade Paciente a partir do DTO e salva no banco
        repository.save(new Paciente(dados));
    }

    /**
     * Lista pacientes ativos com paginação.
     *
     * Endpoint: GET /pacientes
     * - @PageableDefault: define tamanho padrão de página (10) e ordenação (por nome)
     * - Retorna um Page<DadosListagemPaciente>, mapeando cada entidade para DTO de listagem
     *
     * Parâmetros de URL suportados (exemplos):
     * - ?page=0&pageSize=5&sort=nome,asc
     */
    @GetMapping
    public Page<DadosListagemPaciente> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao
    ) {
        // Busca somente pacientes com ativo=true e converte para DTO de listagem
        return repository
                .findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
    }

    /**
     * Atualiza dados de um paciente existente.
     *
     * Endpoint: PUT /pacientes
     * - @RequestBody: desserializa JSON em DadosAtualizacaoPaciente
     * - @Valid: validação dos campos obrigatórios (por exemplo, id não nulo)
     * - @Transactional: transação automática
     *
     * O método getReferenceById() obtém uma referência “lazy” da entidade sem
     * ir imediatamente ao banco; a atualização só ocorre ao final da transação.
     */
    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        // Obtém a referência ao paciente pelo ID
        var paciente = repository.getReferenceById(dados.id());
        // Aplica somente os campos não nulos do DTO na entidade
        paciente.atualizarInformacoes(dados);
    }

    /**
     * “Exclui” um paciente logicamente, marcando-o como inativo.
     *
     * Endpoint: DELETE /pacientes/{id}
     * - @PathVariable: captura o valor do ID na URL
     * - @Transactional: garante que a alteração (ativo=false) seja persistida
     */
    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        // Obtém a referência ao paciente e marca como inativo
        var paciente = repository.getReferenceById(id);
        paciente.excluir();
    }

}
