package com.mindmatch.pagamento.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IndicadoresRepository {

    private final JdbcTemplate jdbcTemplate;

    public IndicadoresRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public BigDecimal ticketMedioCliente(long clienteId){
        String sql = "SELECT FN_TICKET_MEDIO_CLIENTE(?) AS VAL FROM dual";
        PreparedStatementSetter pss = ps -> ps.setLong(1, clienteId);
        RowMapper<BigDecimal> rm = (rs, rowNum) -> rs.getBigDecimal("VAL");
        return jdbcTemplate.query(con -> {
            var ps = con.prepareStatement(sql);
            pss.setValues(ps);
            return ps;
        }, rs -> rs.next() ? rm.mapRow(rs, 1) : BigDecimal.ZERO);
    }

    public String descricaoPagamentoFormatada(long pagamentoId){
        String sql = "SELECT FN_DESCRICAO_PAGAMENTO(?) AS TXT FROM dual";
        PreparedStatementSetter pss = ps -> ps.setLong(1, pagamentoId);
        RowMapper<String> rm = (rs, rowNum) -> rs.getString("TXT");
        return jdbcTemplate.query(con -> {
            var ps = con.prepareStatement(sql);
            pss.setValues(ps);
            return ps;
        }, rs -> rs.next() ? rm.mapRow(rs, 1) : null);
    }

    public int registrarAlertasAcimaDe(double limite){
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PRC_REGISTRAR_ALERTAS")
                .declareParameters(
                        new SqlParameter("p_limite", Types.NUMERIC),
                        new SqlOutParameter("o_qtd", Types.NUMERIC)
                );
        Map<String, Object> in = new HashMap<>();
        in.put("p_limite", limite);
        Map<String, Object> out = call.execute(in);
        Number qtd = (Number) out.get("O_QTD");
        return qtd == null ? 0 : qtd.intValue();
    }

    public List<Map<String,Object>> relatorioConsumoCliente(long clienteId){
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PRC_RELATORIO_CONSUMO_CLIENTE")
                .declareParameters(new SqlParameter("p_id_cliente", Types.NUMERIC))
                .returningResultSet("o_cursor", (rs, rowNum) -> {
                    Map<String,Object> m = new HashMap<>();
                    m.put("mes", rs.getString("MES"));
                    m.put("qtd", rs.getInt("QTD"));
                    m.put("total", rs.getBigDecimal("TOTAL"));
                    return m;
                });
        Map<String, Object> out = call.execute(new MapSqlParameterSource("p_id_cliente", clienteId));
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> lista = (List<Map<String,Object>>) out.get("o_cursor");
        return lista;
    }

    public List<Map<String,Object>> listarAlertas(){
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PRC_LISTAR_ALERTAS")
                .returningResultSet("o_cursor", (rs, rowNum) -> {
                    Map<String,Object> m = new HashMap<>();
                    m.put("idAlerta", rs.getLong("ID_ALERTA"));
                    m.put("idPagamento", rs.getLong("ID_PAGAMENTO"));
                    m.put("valor", rs.getBigDecimal("VALOR"));
                    m.put("mensagem", rs.getString("MENSAGEM"));
                    m.put("dataAlerta", rs.getTimestamp("DATA_ALERTA"));
                    return m;
                });
        Map<String, Object> out = call.execute(new HashMap<>());
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> lista = (List<Map<String,Object>>) out.get("o_cursor");
        return lista;
    }
}
