package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Usuario;
import br.edu.ifpe.paulista.cinegestor.repository.UsuarioRepository;
import br.edu.ifpe.paulista.cinegestor.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }

        String token = jwtUtil.generateToken(usuario.getLogin(), usuario.getTipo().name());

        return Map.of("token", token, "tipo", usuario.getTipo().name());
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido!");
        }

        token = token.substring(7); // Remover "Bearer "
        jwtUtil.revokeToken(token);

        return ResponseEntity.ok("Logout realizado com sucesso!");
    }
}
