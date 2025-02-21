package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "sessao")
@Getter
@Setter
public class Sessao {

    public enum StatusSessao {
        agendada,
        encerrada
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_filme", nullable = true)
    private Filme filme;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = true)
    private Sala sala;

    @Column(nullable = false)
    private LocalDateTime horario;

    @Lob
    @Column(name = "assentos_ocupados", columnDefinition = "TEXT")
    private String assentosOcupadosJson = "[]";  // Armazena JSON de assentos ocupados

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = true)
    private StatusSessao status;

    // Mét0do para adicionar um assento ocupado
    public void ocuparAssento(String assento) {
        if (status != StatusSessao.agendada) {
            throw new RuntimeException("Não é possível ocupar assento em uma sessão encerrada.");
        }
        Set<String> assentos = getAssentosOcupados();
        assentos.add(assento);
        setAssentosOcupados(assentos);
    }

    // Método para liberar um assento
    public void liberarAssento(String assento) {
        Set<String> assentos = getAssentosOcupados();
        assentos.remove(assento);
        setAssentosOcupados(assentos);
    }

    // Converte JSON para Set<String>
    public Set<String> getAssentosOcupados() {
        return assentosOcupadosJson == null || assentosOcupadosJson.isEmpty() ? new HashSet<>() :
                Set.of(assentosOcupadosJson.replace("[", "").replace("]", "").replace("\"", "").split(","));
    }

    // Converte Set<String> para JSON
    public void setAssentosOcupados(Set<String> assentos) {
        this.assentosOcupadosJson = assentos.isEmpty() ? "[]" : "\"" + String.join("\",\"", assentos) + "\"";
    }
}
