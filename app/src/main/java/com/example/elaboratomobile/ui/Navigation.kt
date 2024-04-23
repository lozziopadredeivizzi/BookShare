package com.example.elaboratomobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.elaboratomobile.ui.screens.login.LoginScreen
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneScreen

sealed class BookShareRoute(
    val route: String,
    val title: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Login : BookShareRoute("login", "Login")
    data object Registrazione : BookShareRoute("registrazione", "Registrazione")

    companion object {
        val routes = setOf(Login, Registrazione)
        val noAppBar = setOf(Login, Registrazione)
        val noBottomBar = setOf(Login)
    }

}

@Composable
fun BookShareNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BookShareRoute.Login.route,
        modifier = modifier
    ) {
        with(BookShareRoute.Login) {
            composable(route) {
                LoginScreen(navController)
            }
        }
        with(BookShareRoute.Registrazione){
            composable(route){
                RegistrazioneScreen()
            }
        }
    }
}
