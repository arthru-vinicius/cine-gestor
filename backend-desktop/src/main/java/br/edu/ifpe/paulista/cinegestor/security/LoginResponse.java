package br.edu.ifpe.paulista.cinegestor.security;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String tipo;
    private String nomeCompleto;
    private Integer id;

    public LoginResponse(String token, String tipo, String nomeCompleto, Integer id) {
        this.token = token;
        this.tipo = tipo;
        this.nomeCompleto = nomeCompleto;
        this.id = id;
    }
}
