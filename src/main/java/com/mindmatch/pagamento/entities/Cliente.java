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
    @Column(length = 200, nullable = false)
    private String nome;
    @Column(length = 200, nullable = false, unique = true)
    private String email;
    @Column(length = 14, nullable = false, unique = true)
    private String telefone;
    @Column(nullable = false)
    private Double valorMedioCompra; // Futuramente pode ser movido para uma tabela de estatisticas;
}
