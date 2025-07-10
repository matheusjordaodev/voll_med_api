package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST responsável por autenticar usuários e emitir tokens JWT.
 */
@RestController
@RequestMapping("/login") // Define o endpoint base para operações de login
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;  // Gerencia o processo de autenticação pelo Spring Security

    @Autowired
    private TokenService tokenService;     // Serviço para geração de tokens JWT

    /**
     * Recebe credenciais do usuário, realiza autenticação e retorna um token JWT.
     *
     * @param dados DTO com login e senha fornecidos pelo cliente
     * @return ResponseEntity contendo o token JWT em caso de sucesso
     */
    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(
            @RequestBody @Valid DadosAutenticacao dados  // Desserializa JSON e aplica validações
    ) {
        // Cria o token de autenticação com login e senha
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                dados.login(),   // Nome de usuário
                dados.senha()    // Senha fornecida
        );

        // Realiza a autenticação; lança exceção se falhar (BadCredentials)
        var authentication = manager.authenticate(authenticationToken);

        // Gera o token JWT usando o usuário autenticado (principal)
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        // Retorna 200 OK com o token encapsulado no DTO DadosTokenJWT
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
