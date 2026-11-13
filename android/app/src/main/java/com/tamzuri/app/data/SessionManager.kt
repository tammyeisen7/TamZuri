package com.tamzuri.app.data

object SessionManager {
    var token: String? = null
        private set
    var nombre: String? = null
        private set

    fun guardar(r: AuthResponse) {
        token = r.token
        nombre = r.nombre
    }

    fun cerrar() {
        token = null
        nombre = null
    }
}