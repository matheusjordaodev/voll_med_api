package med.voll.api.controller;


import jakarta.transaction.Transactional;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.MedicoRepository;
import med.voll.api.paciente.DadosCadastroPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Page<DadosListagemPaciente> listar(@PageableDefault(size=1) Pageable paginacao){
        return pacienteRepository.findAll(paginacao).map(DadosListagemPaciente::new);
    }
}