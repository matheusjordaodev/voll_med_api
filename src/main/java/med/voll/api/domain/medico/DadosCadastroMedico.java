package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(

        @NotBlank String nome,

        @NotBlank @Email String email,

        @NotNull @Pattern(regexp ="\\d{4,6}") String crm,

        @NotNull Especialidade especialidade,

        @NotBlank String telefone,

        @NotNull @Valid DadosEndereco endereco
    ){
}
