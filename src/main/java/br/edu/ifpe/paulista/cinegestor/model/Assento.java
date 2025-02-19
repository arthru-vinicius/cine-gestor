package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "assento")
@Getter @Setter
public class Assento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(nullable = false)
    private char linha;

    @Column(nullable = false)
    private int coluna;

    @Column(nullable = false)
    private boolean ocupado = false; // Ocupação do assento
}
