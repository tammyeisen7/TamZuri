package com.tamzuri.app.data

data class LoginRequest(val email: String, val password: String)

data class RegistroRequest(
    val nombre: String,
    val email: String,
    val password: String,
    val pin: String,
    val telefono: String
)

data class AuthResponse(val token: String, val id: Long, val nombre: String, val email: String)

data class ContactoRequest(val nombre: String, val telefono: String)

data class ContactoResponse(val id: Long, val nombre: String, val telefono: String)