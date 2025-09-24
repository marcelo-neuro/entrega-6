package com.mindmatch.pagamento.dto;

import com.mindmatch.pagamento.entities.Pagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PagamentoDTO {
    @Schema(description = "Id do pagamento.")
    private Long id;
    @NotNull(message = "Campo requerido")
    @Positive(message = "O valor do pagamento dever ser um número positivo")
    @Schema(description = "Valor do pagamento.")
    private BigDecimal valor;
    @Schema(description = "Data da transação.")
    @NotNull(message = "Campo requerido")
    private LocalDateTime dataTransacao;
    @Schema(description = "Descrição do pagamento")
    @NotBlank(message = "Campo requerido")
    private String descricao;

    @NotNull(message = "Campo requerido")
    private Long clienteId;
    @NotNull(message = "Campo requerido")
    private Long cartaoId;

    public PagamentoDTO(Pagamento entity) {
        this.id = entity.getId();
        this.valor = entity.getValor();
        this.dataTransacao = entity.getData();
        this.descricao = entity.getDescricao();
        this.clienteId = entity.getCliente().getId();
        this.cartaoId = entity.getCartao().getId();
    }
}
