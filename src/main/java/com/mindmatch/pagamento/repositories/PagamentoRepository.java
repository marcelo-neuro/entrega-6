package com.mindmatch.pagamento.repositories;

import com.mindmatch.pagamento.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("""
            SELECT p FROM Pagamento p
            JOIN p.cliente c
            WHERE c.id = :id
            """)
    List<Pagamento> findByClienteId(Long id);
}
