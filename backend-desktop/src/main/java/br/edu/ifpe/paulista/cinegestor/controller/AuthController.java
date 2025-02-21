package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Usuario;
import br.edu.ifpe.paulista.cinegestor.repository.UsuarioRepository;
import br.edu.ifpe.paulista.cinegestor.security.JWTUtil;
import br.edu.ifpe.paulista.cinegestor.security.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JWTUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Usuario loginRequest) {
        Usuario usuario = usuarioRepository.findByLogin(loginRequest.getLogin())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta!");
        }

        String token = jwtUtil.generateToken(usuario.getLogin(), usuario.getTipo().name());
        LoginResponse response = new LoginResponse(token, usuario.getTipo().name(), usuario.getNomeCompleto(), usuario.getId());
        return ResponseEntity.ok(response);
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
