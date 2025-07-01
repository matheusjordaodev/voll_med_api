package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.DadosEndereco;
public record DadosCadastroPaciente(
        String nome,
        String cpf,
        String email,
        String telefone,
        DadosEndereco endereco
) {
}
