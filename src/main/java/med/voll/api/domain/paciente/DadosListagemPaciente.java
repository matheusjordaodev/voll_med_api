package med.voll.api.domain.paciente;

/**
 * DTO (Data Transfer Object) usado para retornar apenas os dados necessários
 * em uma listagem de pacientes, evitando expor toda a entidade Paciente.
 *
 * Componentes do record:
 * @param id    Identificador único do paciente
 * @param nome  Nome completo do paciente
 * @param email E-mail de contato do paciente
 * @param cpf   CPF (Cadastro de Pessoa Física) do paciente
 */
public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String cpf
) {

    /**
     * Construtor que recebe a entidade Paciente e extrai seus dados para
     * popular este DTO.
     * Isso desacopla a camada de domínio da representação enviada ao cliente.
     *
     * @param paciente a entidade Paciente do banco de dados
     */
    public DadosListagemPaciente(Paciente paciente) {
        // Chama o construtor principal do record, passando apenas os campos desejados
        this(
                paciente.getId(),      // obtém o ID da entidade
                paciente.getNome(),    // obtém o nome da entidade
                paciente.getEmail(),   // obtém o email da entidade
                paciente.getCpf()      // obtém o CPF da entidade
        );
    }

}
