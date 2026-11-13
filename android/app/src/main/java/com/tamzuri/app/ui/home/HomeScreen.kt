package com.tamzuri.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamzuri.app.data.SessionManager

@Composable
fun HomeScreen(onIrAContactos: () -> Unit, onCerrarSesion: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hola, ${SessionManager.nombre ?: ""} 👋", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        Button(onClick = onIrAContactos, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("MIS CONTACTOS DE CONFIANZA")
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("INICIAR RUTA (próximamente)")
        }
        Spacer(Modifier.height(32.dp))
        OutlinedButton(onClick = onCerrarSesion) { Text("Cerrar sesión") }
    }
}