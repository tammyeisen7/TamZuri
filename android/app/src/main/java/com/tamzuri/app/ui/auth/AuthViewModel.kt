package com.tamzuri.app.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamzuri.app.data.*
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String, onOk: () -> Unit) {
        ejecutar(onOk) {
            RetrofitClient.api.login(LoginRequest(email.trim(), password))
        }
    }

    fun registro(nombre: String, email: String, password: String, pin: String, telefono: String, onOk: () -> Unit) {
        ejecutar(onOk) {
            RetrofitClient.api.registro(
                RegistroRequest(nombre.trim(), email.trim(), password, pin, telefono.trim())
            )
        }
    }

    private fun ejecutar(onOk: () -> Unit, llamada: suspend () -> AuthResponse) {
        viewModelScope.launch {
            cargando = true
            error = null
            try {
                SessionManager.guardar(llamada())
                onOk()
            } catch (e: Exception) {
                error = e.mensajeError()
            } finally {
                cargando = false
            }
        }
    }
}