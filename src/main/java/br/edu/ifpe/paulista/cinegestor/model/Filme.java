package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "filme")
@Getter @Setter
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @Column(nullable = false)
    private String duracao; // Ex: "2h 15min"

    @Column(nullable = false)
    private String classificacaoEtaria;

    @Column(name = "imagem_caminho")
    private String imagemCaminho; // Caminho da imagem no servidor
}
