package com.tamzuri.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tamzuri.app.data.SessionManager
import com.tamzuri.app.ui.auth.LoginScreen
import com.tamzuri.app.ui.auth.RegistroScreen
import com.tamzuri.app.ui.home.HomeScreen
import com.tamzuri.app.ui.theme.TamZuriTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TamZuriTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    AppNavegacion(Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun AppNavegacion(modifier: Modifier = Modifier) {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = "login", modifier = modifier) {
        composable("login") {
            LoginScreen(
                onLoginOk = { nav.navigate("home") { popUpTo("login") { inclusive = true } } },
                onIrARegistro = { nav.navigate("registro") }
            )
        }
        composable("registro") {
            RegistroScreen(
                onRegistroOk = { nav.navigate("home") { popUpTo("login") { inclusive = true } } },
                onVolver = { nav.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(onCerrarSesion = {
                SessionManager.cerrar()
                nav.navigate("login") { popUpTo("home") { inclusive = true } }
            })
        }
    }
}