package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "precoingresso")
@Getter @Setter
public class PrecoIngresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private BigDecimal preco;

    public enum DiaSemana {
        domingo, segunda, terca, quarta, quinta, sexta, sabado
    }
}
