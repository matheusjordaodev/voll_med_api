## 💻 Sobre o projeto

Voll.med é uma clínica médica fictícia que precisa de um aplicativo para gestão de consultas. O aplicativo deve possuir funcionalidades que permitam o cadastro de médicos e de pacientes, e também o agendamento e cancelamento de consultas.

Enquanto um time de desenvolvimento será responsável pelo aplicativo mobile, o nosso será responsável pelo desenvolvimento da API Rest desse projeto.

---

## ⚙️ Funcionalidades

- [x] CRUD de médicos;
- [x] CRUD de pacientes;
- [ ] Agendamento de consultas(em breve);
- [ ] Cancelamento de consultas(em breve);

---

## 🎨 Layout

O layout da aplicação mobile está disponível neste link: <a href="https://www.figma.com/file/N4CgpJqsg7gjbKuDmra3EV/Voll.med">Figma</a>

---

## 📄 Documentação

A documentação das funcionalidades da aplicação pode ser acessada neste link: <a href="https://trello.com/b/O0lGCsKb/api-voll-med">Trello</a>

---

## 🛠 Tecnologias

As seguintes tecnologias foram utilizadas no desenvolvimento da API Rest do projeto:

- **[Java 17](https://www.oracle.com/java)**
- **[Spring Boot 3](https://spring.io/projects/spring-boot)**
- **[Maven](https://maven.apache.org)**
- **[MySQL](https://www.mysql.com)**
- **[Hibernate](https://hibernate.org)**
- **[Flyway](https://flywaydb.org)**
- **[Lombok](https://projectlombok.org)**

---



Este projeto **med.voll.api** é uma API RESTful para gerenciamento de médicos e pacientes, implementada com Spring Boot e Spring Security. A seguir, apresentamos um resumo das principais etapas e componentes documentados:

## 1. Configuração de Segurança
- **SecurityConfigurations**: define o filtro de segurança (`SecurityFilterChain`) usando lambdas, desativa CSRF e configura sessão stateless.
- **AuthenticationManager**: exposto como bean para injeção em filtros e serviços.
- **PasswordEncoder**: utiliza `BCryptPasswordEncoder` para hashing seguro de senhas.

## 2. Autenticação JWT
- **TokenService**: serviço que gera tokens JWT assinados com HMAC256, incluindo emissor, sujeito (login) e expiração em 2 horas (UTC-3).
- **AutenticacaoController**: endpoint `POST /login` que recebe `DadosAutenticacao` (login e senha), autentica via `AuthenticationManager` e retorna `DadosTokenJWT` com o token gerado.

## 3. Tratamento de Erros
- **TratadorDeErros** (RestControllerAdvice): intercepta exceções:
    - `EntityNotFoundException`: retorna HTTP 404.
    - `MethodArgumentNotValidException`: retorna HTTP 400 com lista de erros de validação de campos.

## 4. Domínio de Pacientes e Médicos
- **Entidades**: `Paciente` e `Medico`, mapeadas com JPA (`@Entity`, `@Table`), incluem atributos, construtores a partir de DTOs e métodos de atualização e exclusão lógica (`ativo = false`).
- **DTOs**:
    - Cadastro: `DadosCadastroPaciente`, `DadosCadastroMedico` (contêm validações Bean Validation).
    - Atualização: `DadosAtualizacaoPaciente`, `DadosAtualizacaoMedico`.
    - Listagem: `DadosListagemPaciente`, `DadosListagemMedico`.
    - Detalhamento: `DadosDetalhamentoMedico`.
- **Endereço**: classe embutida `Endereco` e DTO `DadosEndereco` com validações.

## 5. Repositórios
- **PacienteRepository** e **MedicoRepository**: estendem `JpaRepository` e definem método `findAllByAtivoTrue(Pageable)` para paginação de registros ativos.

## 6. Controladores REST
- **PacienteController** (`/pacientes`): endpoints para cadastrar, listar (paginação), atualizar, excluir logicamente e detalhes de pacientes.
- **MedicoController** (`/medicos`): endpoints para cadastrar (retorna 201 Created com Location), listar, atualizar, excluir e detalhar médicos.

## Como Executar
1. Defina a porta no `application.properties` (ex: `server.port=8081`).
2. Configure propriedade `api.security.token.secret` no `application.properties`.
3. Rode a aplicação com `mvn spring-boot:run` ou execute o JAR gerado.
