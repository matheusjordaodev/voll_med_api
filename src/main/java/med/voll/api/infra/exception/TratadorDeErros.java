package med.voll.api.infra.exception;

// Importa a exceção lançada pelo JPA quando uma entidade não é encontrada
import jakarta.persistence.EntityNotFoundException;
// ResponseEntity permite criar respostas HTTP com status e corpo customizados
import org.springframework.http.ResponseEntity;
// FieldError representa um erro de validação em um campo específico de um objeto
import org.springframework.validation.FieldError;
// Exceção lançada pelo Spring quando um método anotado com @Valid recebe argumentos inválidos
import org.springframework.web.bind.MethodArgumentNotValidException;
// Anotações para capturar exceções de controllers REST
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Classe responsável por interceptar exceções lançadas dentro dos Controllers REST
 * e transformá-las em respostas HTTP adequadas (por exemplo, 404 ou 400).
 */
@RestControllerAdvice
public class TratadorDeErros {

    /**
     * Intercepta EntityNotFoundException em qualquer controller.
     * Retorna HTTP 404 Not Found sem corpo de resposta.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404() {
        // .notFound() -> status 404
        // .build()    -> constrói a resposta sem corpo
        return ResponseEntity.notFound().build();
    }

    /**
     * Intercepta MethodArgumentNotValidException (erros de validação de @Valid).
     * Recebe a exceção para extrair detalhes dos campos inválidos.
     * Retorna HTTP 400 Bad Request com um JSON listando cada erro de campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
        // Pega a lista de FieldError (cada um com campo e mensagem)
        var erros = ex.getFieldErrors();

        // Converte cada FieldError em um DTO (DadosErroValidacao) e transforma em lista
        var listaDto = erros.stream()
                .map(DadosErroValidacao::new)
                .toList();

        // Retorna 400 Bad Request com o corpo contendo a lista de erros
        return ResponseEntity.badRequest().body(listaDto);
    }

    /**
     * Record (Java 16+) que modela um erro de validação de campo:
     * - campo: nome do campo que falhou na validação
     * - mensagem: motivo da falha
     */
    private record DadosErroValidacao(String campo, String mensagem) {
        /**
         * Construtor que recebe um FieldError e extrai seu nome de campo e mensagem padrão.
         */
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
