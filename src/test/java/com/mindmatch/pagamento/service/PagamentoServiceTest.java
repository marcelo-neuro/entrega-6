package com.mindmatch.pagamento.service;

import com.mindmatch.pagamento.dto.PagamentoDTO;
import com.mindmatch.pagamento.entities.Pagamento;
import com.mindmatch.pagamento.repositories.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository repository;

    @InjectMocks
    private PagamentoService service;

    @Test
    void createPagamento_shouldSetDateAndKeepDescricao() {
        PagamentoDTO dto = new PagamentoDTO(
                null,
                BigDecimal.valueOf(123.45),
                LocalDateTime.now(),
                "Compra: teste",
                1L,
                1L
        );

        when(repository.save(any(Pagamento.class))).thenAnswer(invocation -> {
            Pagamento p = invocation.getArgument(0);
            p.setId(99L);
            return p;
        });

        PagamentoDTO result = service.createPagamento(dto);

        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals(dto.getDescricao(), result.getDescricao());
        assertNotNull(result.getDataTransacao());
    }
}
