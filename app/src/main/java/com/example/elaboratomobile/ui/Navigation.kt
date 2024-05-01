package com.example.elaboratomobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.elaboratomobile.data.models.Theme
import com.example.elaboratomobile.ui.screens.aspetto.AspettoScreen
import com.example.elaboratomobile.ui.screens.aspetto.ThemeState
import com.example.elaboratomobile.ui.screens.books.HomeBooksScreen
import com.example.elaboratomobile.ui.screens.booksDetails.BookDetailsScreen
import com.example.elaboratomobile.ui.screens.chronologyDetails.ChronologyDetails
import com.example.elaboratomobile.ui.screens.login.LoginScreen
import com.example.elaboratomobile.ui.screens.events.EventScreen
import com.example.elaboratomobile.ui.screens.login.LoginViewModel
import com.example.elaboratomobile.ui.screens.modificaEmail.ModificaEmailScreen
import com.example.elaboratomobile.ui.screens.modificaProfilo.ModificaProfiloScreen
import com.example.elaboratomobile.ui.screens.modificaUsername.ModificaUsernameScreen
import com.example.elaboratomobile.ui.screens.profile.ProfileScreen
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneScreen
import com.example.elaboratomobile.ui.screens.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

sealed class BookShareRoute(
    val route: String, val title: String, val arguments: List<NamedNavArgument> = emptyList()
) {
    data object Login : BookShareRoute("login", "Login")
    data object Registrazione : BookShareRoute("registrazione", "Registrazione")
    data object HomeBooks : BookShareRoute("libri", "Libri")
    data object Events : BookShareRoute("eventi", "Eventi")
    data object FavoriteBooks : BookShareRoute("preferiti", "Preferiti")
    data object Profile : BookShareRoute("profilo", "Profilo")
    data object Settings : BookShareRoute("impostazioni", "Impostazioni")
    data object Aspetto : BookShareRoute("aspetto", "Aspetto")
    data object Chronology : BookShareRoute("cronologia", "Cronologia")
    data object ModificaProfilo : BookShareRoute("modificaProfilo", "Modifica Profilo")
    data object ModificaUsername : BookShareRoute("modificaUsername", "Modifica Username")
    data object ModificaEmail : BookShareRoute("modificaEmail", "Modifica E-mail")

    data object BookDetails : BookShareRoute(
        "libriDettagli{bookId}", "Libri Dettagli"
    )

    data object ChronologyDetails :
        BookShareRoute(
            "cronologiaDettagli{bookId}", "Valuta Libro"
        )

    companion object {
        val routes = setOf(
            Login,
            Registrazione,
            HomeBooks,
            Events,
            BookDetails,
            FavoriteBooks,
            Profile,
            Settings,
            Aspetto,
            Chronology,
            ChronologyDetails,
            ModificaProfilo,
            ModificaUsername,
            ModificaEmail
        )
        val noAppBar = setOf(Login, Registrazione)
        val noBottomBar = setOf(
            Login,
            Registrazione,
            BookDetails,
            FavoriteBooks,
            Settings,
            Aspetto,
            Chronology,
            ChronologyDetails,
            ModificaProfilo,
            ModificaUsername,
            ModificaEmail
        )
    }

}

@Composable
fun BookShareNavGraph(
    navController: NavHostController, modifier: Modifier = Modifier, themeState: ThemeState, onThemeSelected: (theme: Theme) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BookShareRoute.Login.route,
        modifier = modifier
    ) {
        with(BookShareRoute.Login) {
            composable(route) {
                val loginVm = koinViewModel<LoginViewModel>()
                val state by loginVm.state.collectAsStateWithLifecycle()
                LoginScreen(
                    state = state,
                    actions = loginVm.actions,
                    onSubmit = { loginVm.login() },
                    navController = navController
                )
            }
        }
        with(BookShareRoute.Registrazione) {
            composable(route) {
                RegistrazioneScreen(navController)
            }
        }
        with(BookShareRoute.HomeBooks) {
            composable(route) {
                HomeBooksScreen(
                    navController,
                    (1..20).toList(),
                    filter = true,
                    nextRoute = BookShareRoute.BookDetails
                )
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
                HomeBooksScreen(
                    navController = navController,
                    (1..5).toList(),
                    filter = false,
                    nextRoute = BookShareRoute.BookDetails
                )
            }
        }
        with(BookShareRoute.Profile) {
            composable(route) {
                ProfileScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.Settings) {
            composable(route) {
                SettingsScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.Aspetto) {
            composable(route) {
                AspettoScreen(themeState, onThemeSelected, navHostController = navController)
            }
        }
        with(BookShareRoute.Chronology) {
            composable(route) {
                HomeBooksScreen(
                    navController = navController,
                    list = (1..6).toList(),
                    filter = false,
                    nextRoute = BookShareRoute.ChronologyDetails
                )
            }
        }
        with(BookShareRoute.ChronologyDetails) {
            composable(route) {
                ChronologyDetails(navController = navController)
            }
        }
        with(BookShareRoute.ModificaProfilo) {
            composable(route) {
                ModificaProfiloScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.ModificaUsername) {
            composable(route) {
                ModificaUsernameScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.ModificaEmail) {
            composable(route) {
                ModificaEmailScreen(navHostController = navController)
            }
        }
    }
}
