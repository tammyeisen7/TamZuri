package com.tamzuri.app.ui.contactos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tamzuri.app.data.ContactoResponse

@Composable
fun ContactosScreen(onVolver: () -> Unit, vm: ContactosViewModel = viewModel()) {
    var mostrarDialogo by remember { mutableStateOf(false) }
    var editando by remember { mutableStateOf<ContactoResponse?>(null) }
    var aBorrar by remember { mutableStateOf<ContactoResponse?>(null) }

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onVolver) { Text("← Volver") }
                Spacer(Modifier.width(8.dp))
                Text("Contactos de confianza", style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.height(12.dp))

            vm.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            when {
                vm.cargando && vm.contactos.isEmpty() ->
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                vm.contactos.isEmpty() ->
                    Text("Aún no tienes contactos. Pulsa + para añadir uno.")
                else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(vm.contactos, key = { it.id }) { c ->
                        Card(Modifier.fillMaxWidth()) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(c.nombre, fontWeight = FontWeight.Bold)
                                    Text(c.telefono, style = MaterialTheme.typography.bodyMedium)
                                }
                                TextButton(onClick = { editando = c; mostrarDialogo = true }) {
                                    Text("Editar")
                                }
                                TextButton(onClick = { aBorrar = c }) {
                                    Text("Borrar", color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { editando = null; mostrarDialogo = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp)
        ) {
            Text("+", fontSize = 26.sp)
        }
    }

    if (mostrarDialogo) {
        ContactoDialog(
            contacto = editando,
            onCerrar = { mostrarDialogo = false },
            onGuardar = { nombre, telefono ->
                vm.guardar(editando?.id, nombre, telefono)
                mostrarDialogo = false
            }
        )
    }

    aBorrar?.let { c ->
        AlertDialog(
            onDismissRequest = { aBorrar = null },
            title = { Text("Borrar contacto") },
            text = { Text("¿Seguro que quieres borrar a ${c.nombre}?") },
            confirmButton = {
                TextButton(onClick = { vm.borrar(c.id); aBorrar = null }) {
                    Text("Borrar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { aBorrar = null }) { Text("Cancelar") } }
        )
    }
}

@Composable
fun ContactoDialog(
    contacto: ContactoResponse?,
    onCerrar: () -> Unit,
    onGuardar: (String, String) -> Unit
) {
    var nombre by remember(contacto) { mutableStateOf(contacto?.nombre ?: "") }
    var telefono by remember(contacto) { mutableStateOf(contacto?.telefono ?: "") }

    AlertDialog(
        onDismissRequest = onCerrar,
        title = { Text(if (contacto == null) "Nuevo contacto" else "Editar contacto") },
        text = {
            Column {
                OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, singleLine = true)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    telefono, { telefono = it }, label = { Text("Teléfono") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onGuardar(nombre, telefono) },
                enabled = nombre.isNotBlank() && telefono.isNotBlank()
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onCerrar) { Text("Cancelar") } }
    )
}