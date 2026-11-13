package com.tamzuri.backend.controller;

import com.tamzuri.backend.dto.ContactoRequest;
import com.tamzuri.backend.dto.ContactoResponse;
import com.tamzuri.backend.model.Usuario;
import com.tamzuri.backend.service.ContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;

    @GetMapping
    public List<ContactoResponse> listar(@AuthenticationPrincipal Usuario u) {
        return contactoService.listar(u.getId());
    }

    @GetMapping("/{id}")
    public ContactoResponse obtener(@PathVariable Long id, @AuthenticationPrincipal Usuario u) {
        return contactoService.obtener(id, u.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactoResponse crear(@Valid @RequestBody ContactoRequest req, @AuthenticationPrincipal Usuario u) {
        return contactoService.crear(req, u.getId());
    }

    @PutMapping("/{id}")
    public ContactoResponse actualizar(@PathVariable Long id, @Valid @RequestBody ContactoRequest req,
                                       @AuthenticationPrincipal Usuario u) {
        return contactoService.actualizar(id, req, u.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@PathVariable Long id, @AuthenticationPrincipal Usuario u) {
        contactoService.borrar(id, u.getId());
    }
}