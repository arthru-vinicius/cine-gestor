package br.edu.ifpe.paulista.cinegestor.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class JWTFilter implements HandlerInterceptor {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        token = token.substring(7); // Remover "Bearer "

        if (!jwtUtil.isTokenValid(token)) { // Verifica se o token foi revogado ou expirou
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Claims claims = jwtUtil.parseToken(token);
        request.setAttribute("usuario", claims.getSubject());
        request.setAttribute("role", claims.get("role"));

        return true;
    }
}
