package com.tamzuri.backend.dto;

import jakarta.validation.constraints.*;

public record RegistroRequest(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password,
        @NotBlank @Pattern(regexp = "\\d{4}", message = "El PIN debe tener 4 dígitos") String pin,
        String telefono
) {}