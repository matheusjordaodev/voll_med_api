package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

/**
 * DTO (Data Transfer Object) usado para receber os dados enviados pelo cliente
 * quando for atualizar informações de um paciente.
 *
 * Os campos que não forem nulos serão aplicados na entidade Paciente.
 *
 * @param id        Identificador do paciente que será atualizado (obrigatório)
 * @param nome      Novo nome do paciente (opcional; aplica somente se não for nulo)
 * @param telefone  Novo telefone de contato (opcional; aplica somente se não for nulo)
 * @param endereco  Novo endereço (opcional; aplica somente se não for nulo)
 */
public record DadosAtualizacaoPaciente(

        @NotNull(message = "O ID do paciente é obrigatório para atualização")
        Long id,                 // ID do paciente para localizar a entidade a ser atualizada

        String nome,             // Nome atualizado; se for null, mantém o valor atual

        String telefone,         // Telefone atualizado; se for null, mantém o valor atual

        DadosEndereco endereco   // Endereço atualizado; se for null, mantém o valor atual

) {
        // Por ser um record, o Java gera automaticamente:
        // 1. Construtor público que recebe (Long id, String nome, String telefone, DadosEndereco endereco)
        // 2. Métodos de acesso: id(), nome(), telefone(), endereco()
        // 3. Implementações de equals(), hashCode() e toString()
}
