package med.voll.api.controller;


import jakarta.transaction.Transactional;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados) {
        System.out.println(dados);
        pacienteRepository.save(new Paciente(dados));
    }
    @GetMapping
    public Page<DadosListagemPaciente> listar(Pageable paginacao){
        return pacienteRepository.findAll(paginacao).map(DadosListagemPaciente::new);
    }
}