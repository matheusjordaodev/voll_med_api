package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO (Data Transfer Object) para transferência de dados de endereço
 * via API.
 * Contém validações Bean Validation (JSR-380) para garantir formato e
 * presença obrigatória de campos essenciais.
 *
 * @param logradouro  Nome da rua, avenida, etc.; não pode ser vazio
 * @param bairro      Nome do bairro; não pode ser vazio
 * @param cep         CEP no formato “00000000” (8 dígitos); não pode ser vazio
 * @param cidade      Nome da cidade; não pode ser vazio
 * @param uf          Unidade federativa (sigla do estado, ex: “SP”); não pode ser vazio
 * @param complemento Complemento do endereço (ex: “Apto 101”); opcional
 * @param numero      Número do imóvel; opcional
 */
public record DadosEndereco(

        @NotBlank(message = "O logradouro é obrigatório")
        String logradouro,

        @NotBlank(message = "O bairro é obrigatório")
        String bairro,

        @NotBlank(message = "O CEP é obrigatório")
        @Pattern(
                regexp = "\\d{8}",
                message = "O CEP deve conter exatamente 8 dígitos numéricos"
        )
        String cep,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "A UF é obrigatória")
        String uf,

        // Complemento é opcional (pode ser null ou vazio)
        String complemento,

        // Número do imóvel é opcional (pode ser null ou vazio)
        String numero

) {
        // Por ser um record, o Java gera automaticamente:
        // 1. Construtor público que recebe todos os parâmetros acima,
        //    aplicando as validações anotadas.
        // 2. Métodos de acesso (logradouro(), bairro(), cep(), cidade(), uf(), complemento(), numero()).
        // 3. Implementações de equals(), hashCode() e toString().
}
