package med.voll.api.domain.medico;

/**
 * DTO (Data Transfer Object) utilizado para retornar apenas as informações
 * essenciais de um médico em listagens, sem expor toda a entidade.
 *
 * Componentes do record:
 *
 * @param id             Identificador único do médico
 * @param nome           Nome completo do médico
 * @param email          E-mail de contato do médico
 * @param crm            Registro profissional (CRM) do médico
 * @param especialidade  Especialidade médica (enum) do médico
 */
public record DadosListagemMedico(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade
) {

    /**
     * Construtor auxiliar que recebe uma instância de Medico
     * e extrai apenas os campos necessários para este DTO.
     *
     * @param medico entidade Médica carregada do banco de dados
     */
    public DadosListagemMedico(Medico medico) {
        // Chama o construtor principal do record, passando cada atributo desejado
        this(
                medico.getId(),             // ID do médico
                medico.getNome(),           // Nome do médico
                medico.getEmail(),          // E-mail do médico
                medico.getCrm(),            // CRM do médico
                medico.getEspecialidade()   // Especialidade (enum) do médico
        );
    }

}
