package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável por carregar os dados de um usuário para autenticação.
 * Implementa a interface UserDetailsService do Spring Security.
 */
@Service  // Registra esta classe como um bean de serviço no contexto do Spring
public class AutenticacaoService implements UserDetailsService {

    /**
     * Repositório de usuários, injetado pelo Spring.
     * Deve expor método findByLogin(String) que retorna uma entidade Usuario.
     */
    @Autowired
    private UsuarioRepository repository;

    /**
     * Método invocado pelo Spring Security ao processar uma tentativa de login.
     *
     * @param username o login (nome de usuário) fornecido pelo cliente
     * @return UserDetails (no caso, a entidade Usuario) contendo login, senha e roles
     * @throws UsernameNotFoundException se não for encontrado nenhum usuário com o login informado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo campo "login"
        var usuario = repository.findByLogin(username);

        // Se não encontrar, lança exceção para indicar falha na autenticação
        if (usuario == null) {
            throw new UsernameNotFoundException(
                    "Usuário não encontrado com o login: " + username
            );
        }

        // Retorna o objeto Usuario (que implementa UserDetails) para o Spring Security
        return usuario;
    }
}
