package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        System.out.println(dados);
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        //Quando Salvo os dados na API, devo salvar com o código 201, e devolver no cporpo da resposta,
        //o recurso registrado com sucesso na aplicação e o recurso do cabeçalho do protocolo HTTP.(location)
        //Que é onde o frontend consegue visualizar e usar os dados.

        //como criar um objeto uri? A URI Tem que ser o endereço da api"
        //O Spring tem uma classe que encapsula o endereço da URI, que a mesma faz a construção da URI da maneira dinâmica.

        //toUri cria um objeto do tipo URI.
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size=2,sort={"nome"}) Pageable paginacao){
        //return medicoRepository.findAll(paginacao).map(DadosListagemMedico::new);
        var page =  medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        //Retorno Padrão Usando o Response Entity do Próprio SpringBoot. (Padrão para get)
        return ResponseEntity.ok(page);
    }

    //JPA faz o todo update, internamente.
    //DTO evita o uso de Ataque via Injection a api ou a aplicação.

    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());

        medico.atualizarInformacoes(dados);
        return  ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        //Não é recomendado devolver uma entidade JPA, é recomendado devolver uM DTO do Objeto.

    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){ //ResponseEntity é um formato padrão do Spring.
        //Como capturar o id ? Com o PathVariable ele obtém essa informação a partir do dado que é fornecido no DeleteMapping.
        var medico = medicoRepository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){ //ResponseEntity é um formato padrão do Spring.
        //Como capturar o id ? Com o PathVariable ele obtém essa informação a partir do dado que é fornecido no DeleteMapping.
        var medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));


    }
}
