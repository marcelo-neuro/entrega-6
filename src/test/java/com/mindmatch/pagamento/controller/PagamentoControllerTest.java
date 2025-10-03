package com.mindmatch.pagamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindmatch.pagamento.dto.PagamentoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPagamentos_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/pagamentos")).andExpect(status().isOk());
    }

    @Test
    void postPagamento_requiresAuth() throws Exception {
        PagamentoDTO dto = new PagamentoDTO(
                null,
                BigDecimal.valueOf(10.0),
                LocalDateTime.now(),
                "Teste",
                1L,
                1L
        );
        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/pagamentos")
                        .with(httpBasic("manager","!L3tm3iN!"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }
}
