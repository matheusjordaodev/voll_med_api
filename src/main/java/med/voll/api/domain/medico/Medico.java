package med.voll.api.domain.medico;

import jakarta.persistence.*;               // Anotações JPA para mapeamento ORM
import lombok.AllArgsConstructor;           // Lombok: gera construtor com todos os campos
import lombok.EqualsAndHashCode;            // Lombok: gera equals() e hashCode() baseado em campos
import lombok.Getter;                       // Lombok: gera getters para todos os campos
import lombok.NoArgsConstructor;            // Lombok: gera construtor sem argumentos
import med.voll.api.domain.endereco.Endereco; // Classe embutida que representa endereço do médico

/**
 * Entidade JPA que representa um médico no sistema.
 * Cada instância é armazenada na tabela "medicos" do banco de dados.
 */
@Table(name = "medicos")                    // Define o nome da tabela no banco
@Entity(name = "Medico")                    // Marca esta classe como uma entidade JPA
@Getter                                     // Gera automaticamente métodos getX() para todos os campos
@NoArgsConstructor                          // Gera automaticamente um construtor sem parâmetros
@AllArgsConstructor                         // Gera automaticamente um construtor com todos os campos
@EqualsAndHashCode(of = "id")               // Gera equals() e hashCode() usando apenas o campo "id"
public class Medico {

    @Id                                      // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                         // Identificador único, gerado automaticamente pelo banco

    private String nome;                     // Nome completo do médico
    private String email;                    // E-mail de contato do médico
    private String telefone;                 // Número de telefone de contato

    private String crm;                      // CRM (registro profissional) do médico

    @Enumerated(EnumType.STRING)            // Armazena o nome da enum no banco (e.g. "CARDIOLOGIA")
    private Especialidade especialidade;     // Especialidade médica (enum)

    @Embedded                                // Indica que os campos de Endereco são mapeados na mesma tabela
    private Endereco endereco;               // Objeto que encapsula rua, cidade, etc.

    private Boolean ativo;                   // Flag indicando se o médico está ativo (true) ou foi "excluído" logicamente (false)

    /**
     * Construtor que recebe DTO de cadastro de médico e inicializa a entidade.
     * - Seta 'ativo' como true por padrão
     * - Copia nome, email, telefone, crm e especialidade do DTO
     * - Cria um novo Endereco a partir dos dados do DTO
     *
     * @param dados Dados de cadastro recebidos da API (nome, email, telefone, crm, especialidade e endereço)
     */
    public Medico(DadosCadastroMedico dados) {
        this.ativo = true;                    // Marca o médico como ativo ao cadastrar
        this.nome = dados.nome();             // Define o nome vindo do DTO
        this.email = dados.email();           // Define o email vindo do DTO
        this.telefone = dados.telefone();     // Define o telefone vindo do DTO
        this.crm = dados.crm();               // Define o CRM vindo do DTO
        this.especialidade = dados.especialidade(); // Define a especialidade vinda do DTO
        this.endereco = new Endereco(dados.endereco()); // Constrói o Endereco embutido
    }

    /**
     * Atualiza somente os campos não nulos do DTO de atualização.
     * - Se nome for informado, sobrescreve o nome atual
     * - Se telefone for informado, sobrescreve o telefone atual
     * - Se endereço for informado, delega a atualização ao objeto Endereco
     *
     * @param dados DTO que contém apenas os campos que devem ser alterados
     */
    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();         // Atualiza o nome se fornecido
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone(); // Atualiza o telefone se fornecido
        }
        if (dados.endereco() != null) {
            // Atualiza campos internos do Endereco (rua, cidade, etc.)
            this.endereco.atualizarInformacoes(dados.endereco());
        }
        // Não atualizamos CRM nem especialidade neste método (poderia estender se necessário)
    }

    /**
     * "Exclusão" lógica: marca o médico como inativo
     * sem remover o registro fisicamente do banco.
     */
    public void excluir() {
        this.ativo = false;                  // Seta ativo para false, ocultando o médico das listagens
    }
}
