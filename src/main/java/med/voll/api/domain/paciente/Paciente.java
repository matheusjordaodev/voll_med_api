package med.voll.api.domain.paciente;
import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;


@Table(name="paciente")
@Entity(name="Paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Paciente {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private Endereco endereco;


    public Paciente(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.endereco = new Endereco(dados.endereco());
    }

}
