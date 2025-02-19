package br.edu.ifpe.paulista.cinegestor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_completo", nullable = false, length = 100)
    private String nomeCompleto;

    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(length = 15)
    private String contato;

    @Column(unique = true, nullable = false, length = 50)
    private String login;

    @Column(nullable = false, length = 50)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    public enum TipoUsuario {
        ADMINISTRADOR, FUNCIONARIO
    }
}
