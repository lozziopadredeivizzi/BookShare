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
import com.example.elaboratomobile.ui.screens.modificaPassword.ModificaPasswordScreen
import com.example.elaboratomobile.ui.screens.modificaProfilo.ModificaProfiloScreen
import com.example.elaboratomobile.ui.screens.profile.ProfileScreen
import com.example.elaboratomobile.ui.screens.profile.ProfileViewModel
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneScreen
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneViewModel
import com.example.elaboratomobile.ui.screens.settings.SettingsScreen
import com.example.elaboratomobile.ui.screens.share.BooksViewModel
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
    data object ModificaPassword : BookShareRoute("modificaPassword", "Modifica Password")
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
            ModificaPassword,
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
            ModificaPassword,
            ModificaEmail
        )
    }

}

@Composable
fun BookShareNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    themeState: ThemeState,
    onThemeSelected: (theme: Theme) -> Unit
) {
    val homebookVm = koinViewModel<BooksViewModel>()
    val bookHomeState by homebookVm.booksState.collectAsStateWithLifecycle()
    val generiState by homebookVm.generiState.collectAsStateWithLifecycle()
    val currentState by homebookVm.selectedGenre.collectAsStateWithLifecycle()


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
                val signUpVm = koinViewModel<RegistrazioneViewModel>()
                val state by signUpVm.state.collectAsStateWithLifecycle()
                RegistrazioneScreen(
                    state = state,
                    actions = signUpVm.actions,
                    onSubmit = { signUpVm.signUp() },
                    navController = navController
                )
            }
        }
        with(BookShareRoute.HomeBooks) {
            composable(route) {

                HomeBooksScreen(
                    navController,
                    bookHomeState.books,
                    generiState.generi,
                    filter = true,
                    nextRoute = BookShareRoute.BookDetails,
                    like = { bookId -> homebookVm.updateLikeStatus(bookId)},
                    comboAction = {genereId -> homebookVm.setSelectedGenre(genereId)},
                    currentIdGenere = currentState
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
                    bookHomeState.books,
                    generiState.generi,
                    filter = false,
                    nextRoute = BookShareRoute.BookDetails,
                    like = { bookId -> homebookVm.updateLikeStatus(bookId)},
                    comboAction = {genereId -> homebookVm.setSelectedGenre(genereId)},
                    currentIdGenere = currentState
                )
            }
        }
        with(BookShareRoute.Profile) {
            composable(route) {
                val profileVm = koinViewModel<ProfileViewModel>()
                val state by profileVm.user.collectAsStateWithLifecycle()
                val num by profileVm.num.collectAsStateWithLifecycle()
                ProfileScreen(
                    user = state,
                    num,
                    navHostController = navController
                )
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
                    bookHomeState.books,
                    generiState.generi,
                    filter = false,
                    nextRoute = BookShareRoute.ChronologyDetails,
                    like = { bookId -> homebookVm.updateLikeStatus(bookId)},
                    comboAction = {genereId -> homebookVm.setSelectedGenre(genereId)},
                    currentIdGenere = currentState
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
        with(BookShareRoute.ModificaPassword) {
            composable(route) {
                ModificaPasswordScreen(navHostController = navController)
            }
        }
        with(BookShareRoute.ModificaEmail) {
            composable(route) {
                ModificaEmailScreen(navHostController = navController)
            }
        }
    }
}
