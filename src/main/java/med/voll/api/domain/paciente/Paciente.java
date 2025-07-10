package med.voll.api.domain.paciente;

import jakarta.persistence.*;          // Anotações JPA para mapeamento ORM
import lombok.AllArgsConstructor;      // Lombok gera construtor com todos os campos
import lombok.EqualsAndHashCode;       // Lombok gera equals() e hashCode() baseado em campos
import lombok.Getter;                  // Lombok gera getters para todos os campos
import lombok.NoArgsConstructor;       // Lombok gera construtor sem argumentos
import med.voll.api.domain.endereco.Endereco;  // Classe embutida que representa endereço do paciente

/**
 * Entidade JPA que representa um paciente no sistema.
 * Cada instância é armazenada na tabela "pacientes" do banco de dados.
 */
@Table(name = "pacientes")            // Define o nome da tabela no banco
@Entity(name = "Paciente")            // Marca esta classe como uma entidade JPA
@Getter                               // Gera automaticamente métodos getX() para todos os campos
@NoArgsConstructor                    // Gera automaticamente um construtor sem parâmetros
@AllArgsConstructor                   // Gera automaticamente um construtor com todos os campos
@EqualsAndHashCode(of = "id")         // Gera equals() e hashCode() usando apenas o campo "id"
public class Paciente {

    @Id                                // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                   // Identificador único, gerado automaticamente pelo banco

    private String nome;               // Nome completo do paciente
    private String email;              // Email de contato do paciente
    private String telefone;           // Número de telefone de contato
    private String cpf;                // CPF (Cadastro de Pessoa Física), documento único no Brasil

    @Embedded                          // Indica que os campos de Endereco são mapeados na mesma tabela
    private Endereco endereco;         // Objeto que encapsula rua, bairro, cidade, etc.

    private Boolean ativo;             // Flag indicando se o paciente está ativo (true) ou "excluído" logicamente (false)

    /**
     * Construtor que recebe DTO de cadastro de paciente e inicializa a entidade.
     * - Seta 'ativo' como true por padrão
     * - Copia nome, email, telefone e cpf do DTO
     * - Cria um novo Endereco a partir dos dados do DTO
     *
     * @param dados Dados de cadastro recebidos da API (nome, email, telefone, cpf e endereço)
     */
    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    /**
     * Atualiza somente os campos não nulos do DTO de atualização.
     * - Se nome for informado, sobrescreve o nome atual
     * - Se telefone for informado, sobrescreve o telefone atual
     * - Se endereço for informado, delega a atualização ao objeto Endereco
     *
     * @param dados DTO que contém apenas os campos que devem ser alterados
     */
    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            // Atualiza campos internos do Endereco (rua, cidade, etc.)
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    /**
     * "Exclusão" lógica: marca o paciente como inativo
     * sem remover o registro fisicamente do banco.
     */
    public void excluir() {
        this.ativo = false;
    }
}
