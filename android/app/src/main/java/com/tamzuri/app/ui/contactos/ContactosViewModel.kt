package com.tamzuri.app.ui.contactos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamzuri.app.data.ContactoRequest
import com.tamzuri.app.data.ContactoResponse
import com.tamzuri.app.data.RetrofitClient
import com.tamzuri.app.data.mensajeError
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ContactosViewModel : ViewModel() {

    var contactos by mutableStateOf<List<ContactoResponse>>(emptyList())
        private set
    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        cargar()
    }

    fun cargar() = lanzar {
        contactos = RetrofitClient.api.listarContactos()
    }

    fun guardar(id: Long?, nombre: String, telefono: String) = lanzar {
        val req = ContactoRequest(nombre.trim(), telefono.trim())
        if (id == null) RetrofitClient.api.crearContacto(req)
        else RetrofitClient.api.actualizarContacto(id, req)
        contactos = RetrofitClient.api.listarContactos()
    }

    fun borrar(id: Long) = lanzar {
        val r = RetrofitClient.api.borrarContacto(id)
        if (!r.isSuccessful) throw HttpException(r)
        contactos = RetrofitClient.api.listarContactos()
    }

    private fun lanzar(bloque: suspend () -> Unit) {
        viewModelScope.launch {
            cargando = true
            error = null
            try {
                bloque()
            } catch (e: Exception) {
                error = e.mensajeError()
            } finally {
                cargando = false
            }
        }
    }
}