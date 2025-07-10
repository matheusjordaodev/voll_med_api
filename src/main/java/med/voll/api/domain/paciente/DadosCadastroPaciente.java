package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

/**
 * DTO (Data Transfer Object) usado para receber os dados de cadastro de um paciente
 * via requisição REST.
 * Contém validações JSR-380 (Bean Validation) para garantir integridade dos dados.
 *
 * @param nome     Nome completo do paciente; não pode ser vazio ou apenas espaços
 * @param email    E-mail de contato válido; não pode ser vazio e deve respeitar formato padrão
 * @param telefone Telefone de contato; não pode ser vazio
 * @param cpf      CPF no formato “000.000.000-00”; não pode ser vazio
 * @param endereco Objeto com dados de endereço; não pode ser nulo e seus campos também são validados
 */
public record DadosCadastroPaciente(

        @NotBlank(message = "O nome é obrigatório")                // Garante que nome não seja null nem vazio
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")              // Garante que email não seja null nem vazio
        @Email(message = "Formato de e-mail inválido")             // Garante que o valor siga padrão de e-mail
        String email,

        @NotBlank(message = "O telefone é obrigatório")            // Garante que telefone não seja null nem vazio
        String telefone,

        @NotBlank(message = "O CPF é obrigatório")                 // Garante que cpf não seja null nem vazio
        @Pattern(
                regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}",          // Define máscara “000.000.000-00”
                message = "CPF deve estar no formato 000.000.000-00"
        )
        String cpf,

        @NotNull(message = "O endereço é obrigatório")             // Garante que objeto endereco não seja null
        @Valid                                                     // Dispara validação recursiva dos campos de DadosEndereco
        DadosEndereco endereco

) {
        // Por ser um record, o Java gera automaticamente:
        // 1. Construtor público que recebe todos os parâmetros acima
        // 2. Métodos de acesso (nome(), email(), telefone(), cpf(), endereco())
        // 3. Implementações de equals(), hashCode() e toString()
}
