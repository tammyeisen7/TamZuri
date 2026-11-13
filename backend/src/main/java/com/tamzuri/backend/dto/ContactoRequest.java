package com.tamzuri.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoRequest(
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Size(max = 20) String telefono
) {}