package com.tamzuri.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "pin_seguridad", nullable = false)
    private String pinSeguridad;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String rol = "USER";

    @Column(nullable = false)
    private boolean activo = true;
}