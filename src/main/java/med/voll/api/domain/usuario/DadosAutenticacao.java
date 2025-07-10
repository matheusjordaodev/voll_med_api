package med.voll.api.domain.usuario;

/**
 * DTO (Data Transfer Object) usado para receber os dados de autenticação
 * enviados pelo cliente (por exemplo, no corpo de uma requisição JSON).
 *
 * @param login  nome de usuário informado pelo cliente
 * @param senha  senha informada pelo cliente
 */
public record DadosAutenticacao(
        String login,   // login (username) para tentativa de autenticação
        String senha    // senha correspondente ao login
) {
    // Por ser um record, o Java automaticamente gera:
    // - construtor público que recebe (String login, String senha)
    // - getters (login(), senha())
    // - implementações de equals(), hashCode() e toString()
}
