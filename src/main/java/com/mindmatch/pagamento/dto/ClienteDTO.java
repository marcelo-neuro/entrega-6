package com.mindmatch.pagamento.dto;

import com.mindmatch.pagamento.entities.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {

    @Schema(description = "Identificador do cliente")
    private Long id;
    @Size(min = 3 , max = 200, message = "O nome do cliente deve conter entre 3 e 200 caracteres.")
    @NotBlank(message = "O campo \"nome\" é obrigatório.")
    @Schema(description = "Nome do cliente")
    private String nome;
    @Email(message = "O e-mail do cliente precisa ser válido.")
    @Size(min = 3 , max = 200, message = "O e-mail do cliente deve conter entre 3 e 200 caracteres.")
    @NotBlank(message = "O campo \"email\" é obrigatório.")
    @Schema(description = "E-mail do cliente")
    private String email;
    @Size(min = 9, max = 14, message = "O telefone do cliente deve conter entre 9 e 14 caracteres.")
    @NotBlank(message = "O campo \"telefone\" é obrigatório.")
    @Schema(description = "Número de telefone/celular do cliente")
    private String telefone;
    @Positive(message = "O campo deve ser positivo.")
    @Schema(description = "Valor médio de compra do cliente")
    private Double valorMedioCompra;

    public ClienteDTO(Cliente entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.telefone = entity.getTelefone();
        this.valorMedioCompra = entity.getValorMedioCompra();
    }
}
