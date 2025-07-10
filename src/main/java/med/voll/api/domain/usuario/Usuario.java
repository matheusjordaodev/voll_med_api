package med.voll.api.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entidade que representa um usuário do sistema, armazenada na tabela "usuarios".
 * Implementa UserDetails para integrar com o Spring Security.
 */
@Table(name = "usuarios")               // Mapeia esta classe para a tabela "usuarios"
@Entity(name = "Usuario")               // Marca como entidade JPA com nome lógico "Usuario"
@Getter                                // Lombok: gera getters para todos os campos
@NoArgsConstructor                     // Lombok: gera construtor padrão (sem argumentos)
@AllArgsConstructor                    // Lombok: gera construtor com todos os campos
@EqualsAndHashCode(of = "id")          // Lombok: equals/hashCode baseados apenas no campo "id"
public class Usuario implements UserDetails {

    @Id                                 // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // ID gerado automaticamente pelo banco

    private String login;                // Login (username) do usuário
    private String senha;                // Senha (deverá ser armazenada criptografada)

    /**
     * Retorna as autoridades (roles) atribuídas ao usuário.
     * Aqui sempre retorna ROLE_USER.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority encapsula uma string de permissão/role
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Retorna a senha para o Spring Security processar a autenticação.
     */
    @Override
    public String getPassword() {
        return senha;
    }

    /**
     * Retorna o nome de usuário (login) usado para autenticação.
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * Indica se a conta do usuário expirou.
     * true = não expirou (permanecerá válida).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se a conta do usuário está bloqueada.
     * true = não está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica se as credenciais (senha) expiraram.
     * true = credenciais válidas.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se o usuário está habilitado.
     * true = ativo e pode realizar login.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
