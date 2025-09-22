package com.mindmatch.pagamento.dto;

import com.mindmatch.pagamento.entities.Cartao;
import com.mindmatch.pagamento.entities.TipoCartao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartaoDTO {

    private Long id;
    @Size(min = 16 , max = 20, message = "O numero do cartão deve conter entre 16 e 20 caracteres.")
    @NotBlank(message = "O campo \"numero\" é obrigatório.")
    private String numero;
    @Size(min = 16 , max = 20, message = "O numero do cartão deve conter entre 16 e 20 caracteres.")
    @NotBlank(message = "O campo \"numero\" é obrigatório.")
    private String cvv;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O campo \"numero\" é obrigatório.")
    private TipoCartao tipoCartao;
    @NotNull(message = "O campo \"clienteId\" é obrigatório.")
    private Long clienteId;

    public CartaoDTO(Cartao entity) {
        this.id = entity.getId();
        this.numero = entity.getNumero();
        this.cvv = entity.getCvv();
        this.tipoCartao = entity.getTipoCartao();
        this.clienteId = entity.getCliente().getId();
    }
}
