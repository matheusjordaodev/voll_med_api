package med.voll.api.infra.security;

/**
 * DTO (Data Transfer Object) usado para enviar o token JWT de volta ao cliente
 * após uma autenticação bem-sucedida.
 *
 * @param token String contendo o JWT gerado pelo TokenService
 */
public record DadosTokenJWT(String token) {
    // Por ser um record, o Java gera automaticamente:
    // 1. Campo privado final `token`
    // 2. Construtor público que recebe (String token)
    // 3. Método de acesso `token()` que retorna o valor do campo
    // 4. Implementações de equals(), hashCode() e toString()
}
