package com.mindmatch.pagamento.dto;

import com.mindmatch.pagamento.entities.Cliente;
import jakarta.persistence.Column;
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

    private Long id;
    @Size(min = 3 , max = 200, message = "O nome do cliente deve conter entre 3 e 200 caracteres.")
    @NotBlank(message = "O campo \"nome\" é obrigatório.")
    private String nome;
    @Email(message = "O e-mail do cliente precisa ser válido.")
    @Size(min = 3 , max = 200, message = "O e-mail do cliente deve conter entre 3 e 200 caracteres.")
    private String email;
    @Size(min = 9, max = 14, message = "O telefone do cliente deve conter entre 9 e 14 caracteres.")
    private String telefone;
    @Positive(message = "O campo deve ser positivo.")
    private Double valorMedioCompra;

    public ClienteDTO(Cliente entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.telefone = entity.getTelefone();
        this.valorMedioCompra = entity.getValorMedioCompra();
    }
}
