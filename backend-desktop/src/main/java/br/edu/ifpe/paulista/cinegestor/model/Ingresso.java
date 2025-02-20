package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "ingresso")
@Getter @Setter
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_sessao", nullable = false)
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name = "id_preco", nullable = false)
    private PrecoIngresso precoIngresso;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoIngresso tipo;

    @Column(nullable = false)
    private String assento;

    @Column(nullable = false)
    private BigDecimal precoPago; // Agora armazenamos o valor pago

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    public enum TipoIngresso {
        INTEIRA, MEIA
    }

    public enum MetodoPagamento {
        PIX, CARTAO_CREDITO, CARTAO_DEBITO, DINHEIRO
    }
}
