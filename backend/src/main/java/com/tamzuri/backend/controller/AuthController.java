package com.tamzuri.backend.controller;

import com.tamzuri.backend.dto.AuthResponse;
import com.tamzuri.backend.dto.LoginRequest;
import com.tamzuri.backend.dto.RegistroRequest;
import com.tamzuri.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse registro(@Valid @RequestBody RegistroRequest req) {
        return authService.registrar(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
}