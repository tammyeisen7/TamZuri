package com.tamzuri.backend.repository;

import com.tamzuri.backend.model.ContactoConfianza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactoRepository extends JpaRepository<ContactoConfianza, Long> {
    List<ContactoConfianza> findByUsuarioId(Long usuarioId);
    Optional<ContactoConfianza> findByIdAndUsuarioId(Long id, Long usuarioId);
}