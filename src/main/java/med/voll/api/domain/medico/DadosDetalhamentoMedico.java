package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

/**
 * DTO (Data Transfer Object) usado para enviar ao cliente os detalhes completos de um médico.
 *
 * Este record agrupa somente as informações necessárias para a visualização detalhada,
 * evitando expor diretamente a entidade JPA.
 *
 * Componentes do record:
 *
 * @param id             Identificador único do médico
 * @param nome           Nome completo do médico
 * @param email          E-mail de contato do médico
 * @param crm            Registro profissional (CRM) do médico
 * @param telefone       Telefone de contato do médico
 * @param especialidade  Especialidade médica (enum) do médico
 * @param endereco       Objeto Endereco com dados completos de localização
 */
public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String crm,
        String telefone,
        Especialidade especialidade,
        Endereco endereco
) {

    /**
     * Construtor auxiliar que converte uma entidade Medico em um DTO.
     * Recebe o objeto Medico carregado do banco e extrai somente os campos desejados.
     *
     * @param medico entidade Medico contendo todos os dados no back-end
     */
    public DadosDetalhamentoMedico(Medico medico) {
        this(
                medico.getId(),             // pega o ID do médico
                medico.getNome(),           // pega o nome completo
                medico.getEmail(),          // pega o e-mail
                medico.getCrm(),            // pega o CRM profissional
                medico.getTelefone(),       // pega o telefone de contato
                medico.getEspecialidade(),  // pega a especialidade (enum)
                medico.getEndereco()        // pega o endereço (objeto embutido)
        );
    }
}
