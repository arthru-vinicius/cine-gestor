package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sessao")
@Getter @Setter
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_filme", nullable = false)
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(nullable = false)
    private LocalDateTime horario;

    @ElementCollection
    @CollectionTable(name = "assentos_ocupados", joinColumns = @JoinColumn(name = "sessao_id"))
    @Column(name = "assento")
    private Set<String> assentosOcupados = new HashSet<>();

    public void ocuparAssento(String assento) {
        assentosOcupados.add(assento);
    }

    public void liberarAssento(String assento) {
        assentosOcupados.remove(assento);
    }
}
