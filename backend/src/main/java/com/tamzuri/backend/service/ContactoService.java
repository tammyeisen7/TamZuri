package com.tamzuri.backend.service;

import com.tamzuri.backend.dto.ContactoRequest;
import com.tamzuri.backend.dto.ContactoResponse;
import com.tamzuri.backend.model.ContactoConfianza;
import com.tamzuri.backend.repository.ContactoRepository;
import com.tamzuri.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactoService {

    private final ContactoRepository contactoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ContactoResponse> listar(Long usuarioId) {
        return contactoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    public ContactoResponse obtener(Long id, Long usuarioId) {
        return toResponse(buscar(id, usuarioId));
    }

    public ContactoResponse crear(ContactoRequest req, Long usuarioId) {
        ContactoConfianza c = new ContactoConfianza();
        c.setNombre(req.nombre());
        c.setTelefono(req.telefono());
        c.setUsuario(usuarioRepository.getReferenceById(usuarioId));
        return toResponse(contactoRepository.save(c));
    }

    public ContactoResponse actualizar(Long id, ContactoRequest req, Long usuarioId) {
        ContactoConfianza c = buscar(id, usuarioId);
        c.setNombre(req.nombre());
        c.setTelefono(req.telefono());
        return toResponse(contactoRepository.save(c));
    }

    public void borrar(Long id, Long usuarioId) {
        contactoRepository.delete(buscar(id, usuarioId));
    }

    private ContactoConfianza buscar(Long id, Long usuarioId) {
        return contactoRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contacto no encontrado"));
    }

    private ContactoResponse toResponse(ContactoConfianza c) {
        return new ContactoResponse(c.getId(), c.getNombre(), c.getTelefono());
    }
}