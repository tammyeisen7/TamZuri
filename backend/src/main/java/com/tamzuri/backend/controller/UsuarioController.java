
package com.tamzuri.backend.controller;

import com.tamzuri.backend.dto.UsuarioResponse;
import com.tamzuri.backend.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @GetMapping("/me")
    public UsuarioResponse me(@AuthenticationPrincipal Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getEmail(), u.getTelefono(), u.getRol());
    }
}