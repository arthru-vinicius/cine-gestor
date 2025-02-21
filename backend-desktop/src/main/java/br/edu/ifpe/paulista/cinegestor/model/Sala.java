package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "sala")
@Getter @Setter
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_sala", nullable = false, unique = true)
    private String numeroSala;

    @Column(name = "qtd_cadeiras", nullable = false)
    private int qtdCadeiras;

    @Column(nullable = false)
    private int colunas;

    @Column(nullable = false)
    private int linhas;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "lado_entrada", nullable = false)
//    private LadoEntrada ladoEntrada;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assento> assentos;

    public enum LadoEntrada {
        esquerda, direita
    }
}
