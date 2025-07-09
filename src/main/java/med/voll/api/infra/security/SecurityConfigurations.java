package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // desabilita CSRF sem usar o método obsoleto
                .csrf(AbstractHttpConfigurer::disable)

                // configura sessão como stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // permita estas URLs sem autenticação, por exemplo:
                        .requestMatchers("/login", "/error", "/swagger-ui/**").permitAll()
                        // todas as outras exigem autenticação
                        .anyRequest().authenticated()
                );

        return http.build();
    }
    @Bean
    //Bean serve para exportar uma classe para o Spring, de modo que ele consiga carrega-lo e realizar a sua injeção de dependencias em outras classes.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
