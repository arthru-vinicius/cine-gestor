package br.edu.ifpe.paulista.cinegestor.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private final Set<String> revokedTokens = new HashSet<>(); // Armazena tokens de logout

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        // Garante que a chave tenha pelo menos 32 bytes para evitar erro no JWT
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("A chave secreta do JWT deve ter pelo menos 32 caracteres.");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Usa expirationTime correto
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        if (revokedTokens.contains(token)) { // Verifica se o token foi revogado
            throw new ExpiredJwtException(null, null, "Token foi revogado (logout).");
        }

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date()); // Verifica se está dentro do prazo de validade
        } catch (JwtException e) {
            return false; // Token expirado ou inválido
        }
    }

    public void revokeToken(String token) {
        revokedTokens.add(token); // Adiciona o token à lista de tokens revogados
    }
}
