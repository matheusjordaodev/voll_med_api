package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade Paciente.
 *
 * Extende JpaRepository, que já fornece métodos básicos de CRUD, paginação e ordenação.
 * O Spring Data JPA vai gerar automaticamente a implementação em tempo de execução.
 *
 * Tipos genéricos:
 * - Paciente: tipo da entidade gerenciada
 * - Long: tipo do identificador (chave primária) da entidade
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /**
     * Busca todos os pacientes com o campo 'ativo' == true.
     *
     * @param paginacao objeto Pageable que contém informações de página, tamanho e ordenação
     * @return Page<Paciente> contendo apenas pacientes ativos, conforme parâmetros de paginação
     *
     * A assinatura segue a convenção de nomenclatura do Spring Data JPA:
     * - findAllBy: busca todos os registros que satisfaçam a condição seguinte
     * - AtivoTrue: filtra pelo campo ativo sendo verdadeiro (WHERE ativo = true)
     *
     * O Spring Data JPA interpreta o nome do método e gera a query correspondente automaticamente.
     */
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
}
