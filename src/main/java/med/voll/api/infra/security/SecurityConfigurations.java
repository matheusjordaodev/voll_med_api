package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe responsável por configurar as regras de segurança da aplicação REST.
 * Substitui o antigo WebSecurityConfigurerAdapter, usando o novo modelo de beans.
 */
@Configuration                   // Informa ao Spring que esta classe define beans de configuração
@EnableWebSecurity               // Habilita o suporte ao Spring Security na aplicação
public class SecurityConfigurations {

    /**
     * Define o filtro de segurança (SecurityFilterChain) que será aplicado a todas as requisições HTTP.
     * @param http objeto que permite customizar o comportamento de segurança
     * @return uma cadeia de filtros com a configuração de segurança desejada
     * @throws Exception em caso de erro na construção da cadeia de filtros
     */
    @Bean                         // Registra este método como bean no contexto do Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Desativa proteção CSRF (útil em APIs stateless que não usam sessões de navegador)
        http.csrf().disable();

        // Configura a aplicação para NÃO manter sessões HTTP (stateless, típico em APIs REST)
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Aqui você poderia adicionar regras de autorização, filtros JWT, etc.
        // Exemplo básico: todas as requisições devem estar autenticadas:
        // http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

        // Constroi e retorna a configuração
        return http.build();
    }

    /**
     * Expondo o AuthenticationManager como bean para permitir injeção em outros componentes,
     * por exemplo, em um serviço que realiza autenticação customizada ou em um filter JWT.
     *
     * @param configuration provê acesso à configuração de autenticação do Spring
     * @return o AuthenticationManager padrão configurado pelo Spring
     * @throws Exception em caso de falha ao obter o manager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        // Retorna o AuthenticationManager construído automaticamente pelo Spring Boot
        return configuration.getAuthenticationManager();
    }

    /**
     * Bean que fornece o mecanismo de criptografia de senhas.
     * O BCrypt é atualmente uma das funções de hash mais seguras e recomendadas pelo Spring.
     *
     * @return uma instância de BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt gera salt aleatório e armazena junto no hash resultante
        return new BCryptPasswordEncoder();
    }

}
