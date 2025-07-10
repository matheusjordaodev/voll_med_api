package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

/**
 * DTO (Data Transfer Object) usado para receber os dados de cadastro de um médico
 * via requisição REST.
 * Contém validações JSR-380 (Bean Validation) para garantir integridade dos dados.
 *
 * @param nome          Nome completo do médico; não pode ser vazio
 * @param email         E-mail válido do médico; não pode ser vazio e deve respeitar o formato de e-mail
 * @param telefone      Telefone de contato; não pode ser vazio
 * @param crm           Número do CRM (registro profissional); apenas dígitos, entre 4 e 6 caracteres
 * @param especialidade Especialidade médica; não pode ser nula
 * @param endereco      Dados de endereço do médico; não pode ser nulo e seus campos também são validados
 */
public record DadosCadastroMedico(

        @NotBlank(message = "O nome é obrigatório")           // Garante que nome não seja null nem vazio
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")         // Garante que email não seja null nem vazio
        @Email(message = "Formato de e-mail inválido")        // Valida formato de e-mail
        String email,

        @NotBlank(message = "O telefone é obrigatório")       // Garante que telefone não seja null nem vazio
        String telefone,

        @NotBlank(message = "O CRM é obrigatório")            // Garante que crm não seja null nem vazio
        @Pattern(
                regexp = "\\d{4,6}",                              // Define que crm precisa ter entre 4 e 6 dígitos
                message = "CRM deve conter apenas dígitos (4 a 6 caracteres)"
        )
        String crm,

        @NotNull(message = "A especialidade é obrigatória")  // Garante que especialidade não seja null
        Especialidade especialidade,

        @NotNull(message = "O endereço é obrigatório")        // Garante que endereco não seja null
        @Valid                                                // Dispara validação recursiva dos campos de DadosEndereco
        DadosEndereco endereco

) {
        // Por ser um record, o Java gera automaticamente:
        // 1. Construtor público que recebe todos os parâmetros acima
        // 2. Métodos de acesso: nome(), email(), telefone(), crm(), especialidade(), endereco()
        // 3. Implementações de equals(), hashCode() e toString()
}
