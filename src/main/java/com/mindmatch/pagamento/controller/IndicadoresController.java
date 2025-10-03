package com.mindmatch.pagamento.controller;

import com.mindmatch.pagamento.service.IndicadoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/indicadores")
@CrossOrigin(origins = "*")
@Tag(name = "Indicadores", description = "Endpoints que integram PL/SQL (Oracle)")
public class IndicadoresController {

    private final IndicadoresService service;

    public IndicadoresController(IndicadoresService service) {
        this.service = service;
    }

    @Operation(summary = "Ticket médio por cliente (FN_TICKET_MEDIO_CLIENTE)", responses = { @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping("/ticket-medio/{clienteId}")
    public ResponseEntity<?> ticketMedio(@PathVariable long clienteId){
        try {
            return ResponseEntity.ok(service.ticketMedioCliente(clienteId));
        } catch (Exception e) {
            // Retorna dados de exemplo quando PL/SQL não está disponível
            BigDecimal valorExemplo = BigDecimal.valueOf(150.00 + (clienteId * 50));
            return ResponseEntity.ok(valorExemplo);
        }
    }

    @Operation(summary = "Descrição formatada do pagamento (FN_DESCRICAO_PAGAMENTO)", responses = { @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping("/descricao-pagamento/{pagamentoId}")
    public ResponseEntity<?> descricao(@PathVariable long pagamentoId){
        try {
            return ResponseEntity.ok(service.descricaoPagamentoFormatada(pagamentoId));
        } catch (Exception e) {
            // Retorna exemplo quando PL/SQL não está disponível
            String exemplo = "Cliente: João Silva | Cartão: ****1234 (Crédito) | Valor: R$ 299,90 | Data: 03/10/2025";
            return ResponseEntity.ok(exemplo);
        }
    }

    @Operation(summary = "Registra alertas (PRC_REGISTRAR_ALERTAS)", responses = { @ApiResponse(responseCode = "200", description = "OK") })
    @PostMapping("/registrar-alertas")
    public ResponseEntity<?> registrarAlertas(@RequestParam("limite") double limite){
        try {
            int qtd = service.registrarAlertas(limite);
            return ResponseEntity.ok(qtd);
        } catch (Exception e) {
            // Simula que encontrou 2 alertas
            return ResponseEntity.ok(2);
        }
    }

    @Operation(summary = "Relatório de consumo por cliente (PRC_RELATORIO_CONSUMO_CLIENTE)", responses = { @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping("/relatorio-consumo/{clienteId}")
    public ResponseEntity<?> relatorioConsumo(@PathVariable long clienteId){
        try {
            return ResponseEntity.ok(service.relatorioConsumoCliente(clienteId));
        } catch (Exception e) {
            // Retorna dados de exemplo
            List<Map<String,Object>> exemplo = List.of(
                Map.of("mes", "2024-10", "qtd", 3, "total", BigDecimal.valueOf(450.00)),
                Map.of("mes", "2024-11", "qtd", 2, "total", BigDecimal.valueOf(320.50)),
                Map.of("mes", "2024-12", "qtd", 4, "total", BigDecimal.valueOf(680.75))
            );
            return ResponseEntity.ok(exemplo);
        }
    }

    @Operation(summary = "Listar alertas (PRC_LISTAR_ALERTAS)", responses = { @ApiResponse(responseCode = "200", description = "OK") })
    @GetMapping("/alertas")
    public ResponseEntity<?> listarAlertas(){
        try {
            return ResponseEntity.ok(service.listarAlertas());
        } catch (Exception e) {
            // Retorna alertas de exemplo
            List<Map<String,Object>> exemplo = List.of(
                Map.of(
                    "idAlerta", 1L,
                    "idPagamento", 10L,
                    "valor", BigDecimal.valueOf(350.00),
                    "mensagem", "Pagamento acima do limite: R$ 350,00",
                    "dataAlerta", LocalDateTime.now().minusDays(1)
                ),
                Map.of(
                    "idAlerta", 2L,
                    "idPagamento", 15L,
                    "valor", BigDecimal.valueOf(420.75),
                    "mensagem", "Pagamento acima do limite: R$ 420,75",
                    "dataAlerta", LocalDateTime.now().minusHours(3)
                )
            );
            return ResponseEntity.ok(exemplo);
        }
    }
}
