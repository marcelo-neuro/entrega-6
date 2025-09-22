package com.mindmatch.pagamento.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200)
    private String nome;
    @Column(length = 200)
    private String email;
    @Column(length = 14)
    private String telefone;
    private Double valorMedioCompra; // Futuramente pode ser movido para uma tabela de estatisticas;
}
