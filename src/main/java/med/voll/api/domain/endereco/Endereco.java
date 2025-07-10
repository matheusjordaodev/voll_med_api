package med.voll.api.domain.endereco;

import jakarta.persistence.Embeddable;    // Marca a classe como parte embutida em outra entidade JPA
import lombok.AllArgsConstructor;       // Lombok: gera construtor com todos os campos
import lombok.Getter;                  // Lombok: gera getters para todos os campos
import lombok.NoArgsConstructor;       // Lombok: gera construtor sem argumentos

/**
 * Classe que representa um endereço e seus atributos básicos.
 * Como está anotada com @Embeddable, seus campos serão mapeados
 * diretamente na tabela da entidade que a possuir (por exemplo: Paciente, Médico).
 */
@Embeddable
@Getter
@NoArgsConstructor    // Construtor vazio exigido pelo JPA
@AllArgsConstructor   // Construtor que recebe todos os campos
public class Endereco {

    // Logradouro (rua, avenida, etc.)
    private String logradouro;

    // Bairro do endereço
    private String bairro;

    // CEP no formato brasileiro (ex: "12345-678")
    private String cep;

    // Número do imóvel
    private String numero;

    // Complemento do endereço (ex: "Apto 101", "Fundos")
    private String complemento;

    // Cidade do endereço
    private String cidade;

    // Unidade federativa (estado), ex: "SP", "RJ"
    private String uf;

    /**
     * Construtor que inicializa um Endereco a partir de um DTO (DadosEndereco).
     *
     * @param dados objeto contendo os valores para cada campo do endereço
     */
    public Endereco(DadosEndereco dados) {
        this.logradouro = dados.logradouro();
        this.bairro      = dados.bairro();
        this.cep         = dados.cep();
        this.uf          = dados.uf();
        this.cidade      = dados.cidade();
        this.numero      = dados.numero();
        this.complemento = dados.complemento();
    }

    /**
     * Atualiza apenas os campos não nulos no DTO fornecido.
     * Isso permite alterar parcialmente um endereço sem sobrescrever
     * os atributos que não foram enviados.
     *
     * @param dados objeto com possíveis novos valores para o endereço
     */
    public void atualizarInformacoes(DadosEndereco dados) {
        if (dados.logradouro() != null) {
            this.logradouro = dados.logradouro();
        }
        if (dados.bairro() != null) {
            this.bairro = dados.bairro();
        }
        if (dados.cep() != null) {
            this.cep = dados.cep();
        }
        if (dados.uf() != null) {
            this.uf = dados.uf();
        }
        if (dados.cidade() != null) {
            this.cidade = dados.cidade();
        }
        if (dados.numero() != null) {
            this.numero = dados.numero();
        }
        if (dados.complemento() != null) {
            this.complemento = dados.complemento();
        }
    }
}
