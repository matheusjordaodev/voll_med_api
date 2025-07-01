package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

public record DadosListagemMedico(

        Long Id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade
) {

    public DadosListagemMedico(Medico medico){
        this(medico.getId(),medico.getNome(),medico.getEmail(),medico.getCrm(),medico.getEspecialidade());
    }

    public static record DadosDetalhadosMedico(
            Long id,
            String nome,
            String email,
            String crm,
            Especialidade especialidade,
            Endereco endereco
    ) {

    }
}
