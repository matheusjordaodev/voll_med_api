package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

/**
 * DTO (Data Transfer Object) usado para receber os dados enviados pelo cliente
 * quando for atualizar informações de um médico.
 *
 * Os campos não nulos no payload serão aplicados na entidade Medico existente.
 *
 * @param id        Identificador do médico que será atualizado (obrigatório)
 * @param nome      Novo nome do médico (opcional; se for null, mantém o valor atual)
 * @param telefone  Novo telefone de contato (opcional; se for null, mantém o valor atual)
 * @param endereco  Novo endereço (opcional; se for null, mantém o valor atual)
 */
public record DadosAtualizacaoMedico(

        @NotNull(message = "O ID do médico é obrigatório para atualização")
        Long id,             // ID do médico para localizar a entidade a ser atualizada

        String nome,         // Nome atualizado; se null, não altera o nome atual

        String telefone,     // Telefone atualizado; se null, não altera o telefone atual

        DadosEndereco endereco  // Endereço atualizado; se null, não altera o endereço atual

) {
        // Por ser um record, o Java gera automaticamente:
        // 1. Construtor público que recebe (Long id, String nome, String telefone, DadosEndereco endereco)
        // 2. Métodos de acesso (id(), nome(), telefone(), endereco())
        // 3. Implementações de equals(), hashCode() e toString()
}
