package com.tamzuri.backend.dto;

public record AuthResponse(String token, Long id, String nombre, String email) {}