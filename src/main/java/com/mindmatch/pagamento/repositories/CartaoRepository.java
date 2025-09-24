package com.mindmatch.pagamento.repositories;

import com.mindmatch.pagamento.entities.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    @Query("""
            SELECT ca FROM Cartao ca
            JOIN ca.cliente c
            WHERE c.id = :id
            """)
    List<Cartao> findByClienteId(Long id);
}
