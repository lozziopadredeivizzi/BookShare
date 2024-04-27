package com.example.elaboratomobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.elaboratomobile.ui.screens.aspetto.AspettoScreen
import com.example.elaboratomobile.ui.screens.books.HomeBooksScreen
import com.example.elaboratomobile.ui.screens.booksDetails.BookDetailsScreen
import com.example.elaboratomobile.ui.screens.login.LoginScreen
import com.example.elaboratomobile.ui.screens.events.EventScreen
import com.example.elaboratomobile.ui.screens.profile.ProfileScreen
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneScreen
import com.example.elaboratomobile.ui.screens.settings.SettingsScreen

sealed class BookShareRoute(
    val route: String,
    val title: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Login : BookShareRoute("login", "Login")
    data object Registrazione : BookShareRoute("registrazione", "Registrazione")
    data object HomeBooks : BookShareRoute("libri", "Libri")
    data object Events : BookShareRoute("eventi", "Eventi")
    data object FavoriteBooks : BookShareRoute("preferiti", "Preferiti")
    data object Profile : BookShareRoute("profilo", "Profilo")
    data object  Settings: BookShareRoute("impostazioni", "Impostazioni")
    data object Aspetto: BookShareRoute("aspetto", "Aspetto")

    data object BookDetails : BookShareRoute(
        "libriDettagli{bookId}",
        "Libri Dettagli")


    companion object {
        val routes = setOf(Login, Registrazione, HomeBooks, Events, BookDetails, FavoriteBooks, Profile, Settings, Aspetto)
        val noAppBar = setOf(Login, Registrazione)
        val noBottomBar = setOf(Login, Registrazione, BookDetails, FavoriteBooks, Settings, Aspetto)
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
        with(BookShareRoute.Registrazione) {
            composable(route) {
                RegistrazioneScreen(navController)
            }
        }
        with(BookShareRoute.HomeBooks) {
            composable(route) {
                HomeBooksScreen(navController, (1..20).toList(), filter = true)
            }
        }
        with(BookShareRoute.Events) {
            composable(route) {
                EventScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.BookDetails) {
            composable(route) {
                BookDetailsScreen(navController = navController)
            }
        }
        with(BookShareRoute.FavoriteBooks) {
            composable(route) {
                HomeBooksScreen(navController = navController, (1..5).toList(), filter = false)
            }
        }
        with(BookShareRoute.Profile) {
            composable(route) {
                ProfileScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.Settings){
            composable(route){
                SettingsScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.Aspetto){
            composable(route){
                AspettoScreen(navHostController = navController)
            }
        }
    }
}
