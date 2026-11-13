package com.tamzuri.backend.service;

import com.tamzuri.backend.dto.AuthResponse;
import com.tamzuri.backend.dto.LoginRequest;
import com.tamzuri.backend.dto.RegistroRequest;
import com.tamzuri.backend.model.Usuario;
import com.tamzuri.backend.repository.UsuarioRepository;
import com.tamzuri.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse registrar(RegistroRequest req) {
        if (usuarioRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        Usuario u = new Usuario();
        u.setNombre(req.nombre());
        u.setEmail(req.email());
        u.setPassword(passwordEncoder.encode(req.password()));
        u.setPinSeguridad(passwordEncoder.encode(req.pin()));
        u.setTelefono(req.telefono());
        usuarioRepository.save(u);
        return new AuthResponse(jwtService.generarToken(u), u.getId(), u.getNombre(), u.getEmail());
    }

    public AuthResponse login(LoginRequest req) {
        Usuario u = usuarioRepository.findByEmail(req.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas"));
        if (!passwordEncoder.matches(req.password(), u.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }
        if (!u.isActivo()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuario desactivado");
        }
        return new AuthResponse(jwtService.generarToken(u), u.getId(), u.getNombre(), u.getEmail());
    }
}