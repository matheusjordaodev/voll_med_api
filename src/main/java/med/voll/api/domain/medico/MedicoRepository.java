package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade Medico.
 *
 * Estende JpaRepository para herdar métodos CRUD, paginação e ordenação
 * sem precisar escrever implementações manuais.
 *
 * Tipos genéricos:
 * - Medico: tipo da entidade que este repositório gerencia
 * - Long: tipo do identificador (chave primária) da entidade
 */
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    /**
     * Recupera uma página de médicos que estão marcados como ativos (ativo == true).
     *
     * @param paginacao objeto Pageable contendo informações de página, tamanho e ordenação
     * @return Page<Medico> contendo apenas médicos ativos conforme parâmetros de paginação
     *
     * Convenção de nomenclatura do Spring Data JPA:
     * - findAllBy: busca todos os registros que satisfaçam a condição
     * - AtivoTrue: filtra pelo campo ativo sendo verdadeiro (WHERE ativo = true)
     *
     * O Spring Data interpreta esse nome de método e gera automaticamente a query correspondente.
     */
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);
}
