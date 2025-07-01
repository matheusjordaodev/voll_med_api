package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;
import med.voll.api.medico.Medico;

public record DadosListagemPaciente(
        String nome,
        String email,
        String telefone,
        Endereco endereco
){
    public DadosListagemPaciente(Paciente paciente){

        this(paciente.getNome(),paciente.getEmail(),paciente.getTelefone(),paciente.getEndereco());

    }
}
