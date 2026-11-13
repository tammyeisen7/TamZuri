package com.tamzuri.backend.security;

import com.tamzuri.backend.model.Usuario;
import com.tamzuri.backend.repository.UsuarioRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String email = jwtService.extraerEmail(header.substring(7));
                usuarioRepository.findByEmail(email)
                        .filter(Usuario::isActivo)
                        .ifPresent(u -> {
                            var auth = new UsernamePasswordAuthenticationToken(
                                    u, null, List.of(new SimpleGrantedAuthority("ROLE_" + u.getRol())));
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        });
            } catch (JwtException | IllegalArgumentException e) {
                // Token no válido o caducado: la petición sigue sin autenticar
            }
        }
        chain.doFilter(request, response);
    }
}