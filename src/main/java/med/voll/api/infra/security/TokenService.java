package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Serviço responsável por gerar tokens JWT para autenticação de usuários.
 * Utiliza a biblioteca Auth0 Java JWT para criação e assinatura dos tokens.
 */
@Service   // Registra esta classe como um bean de serviço gerenciado pelo Spring
public class TokenService {

    /**
     * Chave secreta usada para assinar o token.
     * Definida no application.properties (api.security.token.secret).
     */
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT assinado que contém:
     *  - Issuer (quem emitiu o token)
     *  - Subject (identificador do usuário, aqui o login)
     *  - Data de expiração
     *
     * @param usuario instância do usuário autenticado
     * @return String contendo o token JWT pronto para ser enviado ao cliente
     */
    public String gerarToken(Usuario usuario) {
        try {
            // Cria o algoritmo de assinatura HMAC256 usando a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);

            // Monta o token:
            return JWT.create()
                    // Identifica quem gerou o token (pode ser o nome da aplicação)
                    .withIssuer("API Voll.med")
                    // Define o sujeito do token (aqui, o login do usuário)
                    .withSubject(usuario.getLogin())
                    // Define a data de expiração (Instant) calculada abaixo
                    .withExpiresAt(dataExpiracao())
                    // Assina o token com o algoritmo HMAC256 configurado
                    .sign(algoritmo);

        } catch (JWTCreationException exception) {
            // Em caso de falha ao criar ou assinar o token, encapsula a exceção
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    /**
     * Calcula a data/hora de expiração do token.
     * Aqui definimos que o token valerá por 2 horas a partir do momento atual,
     * usando o fuso horário de Brasília (UTC-3).
     *
     * @return Instant representando o timestamp de expiração
     */
    private Instant dataExpiracao() {
        // Pega a data/hora atual, soma 2 horas e converte para Instant com UTC-3
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

}
