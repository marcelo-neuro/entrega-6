package com.mindmatch.pagamento.service;

import com.mindmatch.pagamento.repositories.IndicadoresRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class IndicadoresService {

    private final IndicadoresRepository repo;

    public IndicadoresService(IndicadoresRepository repo) {
        this.repo = repo;
    }

    public BigDecimal ticketMedioCliente(long clienteId){
        return repo.ticketMedioCliente(clienteId);
    }

    public String descricaoPagamentoFormatada(long pagamentoId){
        return repo.descricaoPagamentoFormatada(pagamentoId);
    }

    public int registrarAlertas(double limite){
        return repo.registrarAlertasAcimaDe(limite);
    }

    // Novos métodos integrando procedures com REF CURSOR
    public List<Map<String,Object>> relatorioConsumoCliente(long clienteId){
        return repo.relatorioConsumoCliente(clienteId);
    }

    public List<Map<String,Object>> listarAlertas(){
        return repo.listarAlertas();
    }
}
