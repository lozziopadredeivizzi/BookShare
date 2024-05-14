package com.example.elaboratomobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.elaboratomobile.data.models.Theme
import com.example.elaboratomobile.ui.screens.aspetto.AspettoScreen
import com.example.elaboratomobile.ui.screens.aspetto.ThemeState
import com.example.elaboratomobile.ui.screens.books.HomeBooksScreen
import com.example.elaboratomobile.ui.screens.booksDetails.BookDetailsScreen
import com.example.elaboratomobile.ui.screens.chronologyDetails.ChronologyDetails
import com.example.elaboratomobile.ui.screens.login.LoginScreen
import com.example.elaboratomobile.ui.screens.events.EventScreen
import com.example.elaboratomobile.ui.screens.events.EventsViewModel
import com.example.elaboratomobile.ui.screens.login.LoginViewModel
import com.example.elaboratomobile.ui.screens.modificaEmail.ModificaEmailScreen
import com.example.elaboratomobile.ui.screens.modificaPassword.ModificaPasswordScreen
import com.example.elaboratomobile.ui.screens.modificaProfilo.ModificaProfiloScreen
import com.example.elaboratomobile.ui.screens.profile.ProfileScreen
import com.example.elaboratomobile.ui.screens.profile.ProfileViewModel
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneScreen
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneViewModel
import com.example.elaboratomobile.ui.screens.settings.SettingsScreen
import com.example.elaboratomobile.ui.screens.books.BooksViewModel
import com.example.elaboratomobile.ui.screens.chronology.ChronologyBookViewModel
import com.example.elaboratomobile.ui.screens.books.FavoriteBookViewModel
import com.example.elaboratomobile.ui.screens.booksDetails.BookDetailsViewModel
import com.example.elaboratomobile.ui.screens.chronology.ChronologyBookScreen
import com.example.elaboratomobile.ui.screens.chronologyDetails.ChronologyDetailsViewModel
import com.example.elaboratomobile.ui.screens.loading.LoadingScreen
import com.example.elaboratomobile.ui.screens.loading.LoadingViewModel
import com.example.elaboratomobile.ui.screens.map.MapScreen
import com.example.elaboratomobile.ui.screens.map.MapViewModel
import com.example.elaboratomobile.ui.screens.modificaEmail.ModificaEmailViewModel
import com.example.elaboratomobile.ui.screens.modificaPassword.ModificaPasswordViewModel
import com.example.elaboratomobile.ui.screens.modificaProfilo.ModificaProfiloViewModel
import com.example.elaboratomobile.ui.screens.notification.NotificViewModel
import com.example.elaboratomobile.ui.screens.notification.NotificationScreen
import com.example.elaboratomobile.ui.screens.settings.SettingsViewModel
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
    data object Loading : BookShareRoute("loading", "loading")
    data object Notification : BookShareRoute("notification", "Notifiche")
    data object Map: BookShareRoute("map", "Mappa")

    data object BookDetails : BookShareRoute(
        "libriDettagli/{bookId}", "Dettaglio Libro",
        listOf(navArgument("bookId") { type = NavType.IntType })
    ) {
        fun buildRoute(bookId: Int) = "libriDettagli/$bookId"
    }

    data object ChronologyDetails :
        BookShareRoute(
            "cronologiaDettagli/{prestitoId}", "Valuta Libro",
            listOf(navArgument("prestitoId") { type = NavType.IntType })
        ) {
        fun buildRoute(prestitoId: Int) = "cronologiaDettagli/$prestitoId"
    }

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
            ModificaEmail,
            Loading,
            Map,
            Notification
        )
        val noAppBar = setOf(Login, Registrazione, Loading)
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
            ModificaEmail,
            Loading,
            Notification
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
    NavHost(
        navController = navController,
        startDestination = BookShareRoute.Loading.route,
        modifier = modifier
    ) {
        with(BookShareRoute.Loading) {
            composable(route) {
                val loadingVm = koinViewModel<LoadingViewModel>()
                LoadingScreen(loading = {
                    loadingVm.loading { present ->
                        if (present) {
                            navController.navigate(BookShareRoute.HomeBooks.route)
                        }
                        else {
                            navController.navigate(BookShareRoute.Login.route)
                        }
                    }
                })
            }
        }
        with(BookShareRoute.Login) {
            composable(route) {
                val loginVm = koinViewModel<LoginViewModel>()
                val state by loginVm.state.collectAsStateWithLifecycle()
                LoginScreen(
                    state = state,
                    actions = loginVm.actions,
                    onSubmit = { loginVm.login{success->
                        if(success) {
                            navController.navigate(BookShareRoute.HomeBooks.route)
                        }
                    } },
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
                    onSubmit = { signUpVm.signUp{success->
                        if(success) {
                            navController.navigate(BookShareRoute.HomeBooks.route)
                        }
                    } },
                    navController = navController
                )
            }
        }
        with(BookShareRoute.Notification) {
            composable(route) {
                val notificVm = koinViewModel<NotificViewModel>()
                val notifics by notificVm.notificState.collectAsStateWithLifecycle()
                notifics.forEach{ notific ->
                    notificVm.update(notific.idPossesso)
                }
                NotificationScreen(list = notifics)
            }
        }
        with(BookShareRoute.Map) {
            composable(route) {
                val mapVm = koinViewModel<MapViewModel>()
                val state by mapVm.state.collectAsStateWithLifecycle()
                MapScreen(state = state, actions = mapVm.actions)
            }
        }
        with(BookShareRoute.HomeBooks) {
            composable(route) {
                val homebookVm = koinViewModel<BooksViewModel>()
                val bookHomeState by homebookVm.booksState.collectAsStateWithLifecycle()
                val generiState by homebookVm.generiState.collectAsStateWithLifecycle()
                val currentState by homebookVm.selectedGenre.collectAsStateWithLifecycle()
                HomeBooksScreen(
                    navController,
                    bookHomeState,
                    generiState.generi,
                    like = { bookId -> homebookVm.updateLikeStatus(bookId) },
                    comboAction = { genereId -> homebookVm.setSelectedGenre(genereId) },
                    currentIdGenere = currentState
                )
            }
        }
        with(BookShareRoute.Events) {
            composable(route) {
                val eventVm = koinViewModel<EventsViewModel>()
                val eventsState by eventVm.eventsState.collectAsStateWithLifecycle()
                EventScreen(
                    navHostController = navController,
                    eventsState
                )
            }
        }
        with(BookShareRoute.BookDetails) {
            composable(route, arguments) { backStackEntry ->
                val detailsVm = koinViewModel<BookDetailsViewModel>()
                val bookState by detailsVm.booksState.collectAsStateWithLifecycle()
                val librariesState by detailsVm.librariesState.collectAsStateWithLifecycle()
                val recensioniList by detailsVm.recensioniForLibro.collectAsStateWithLifecycle()
                val idBook = backStackEntry.arguments?.getInt("bookId")
                if (idBook != null) {
                    detailsVm.loadBookAndLibraries(idBook)
                }
                BookDetailsScreen(
                    navController = navController,
                    bookState = bookState,
                    librariesState = librariesState,
                    onSubmit = { id_possesso -> detailsVm.addPrestito(id_possesso) },
                    recensioniList = recensioniList
                )
            }
        }
        with(BookShareRoute.FavoriteBooks) {
            composable(route) {
                val favoriteVm = koinViewModel<FavoriteBookViewModel>()
                val bookFavoriteState by favoriteVm.booksState.collectAsStateWithLifecycle()
                val generiFavoriteState by favoriteVm.generiState.collectAsStateWithLifecycle()
                val currentFavoriteState by favoriteVm.selectedGenre.collectAsStateWithLifecycle()
                HomeBooksScreen(
                    navController = navController,
                    bookFavoriteState,
                    generiFavoriteState.generi,
                    like = { bookId -> favoriteVm.updateLikeStatus(bookId) },
                    comboAction = { genereId -> favoriteVm.setSelectedGenre(genereId) },
                    currentIdGenere = currentFavoriteState
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
                    editImage = {image->profileVm.updatePfpImage(image)},
                    navHostController = navController
                )
            }
        }
        with(BookShareRoute.Settings) {
            composable(route) {
                val settingsVm = koinViewModel<SettingsViewModel>()
                SettingsScreen(
                    navHostController = navController,
                    logOut = {
                        settingsVm.logOut { success ->
                            if (success) {
                                navController.navigate(BookShareRoute.Login.route)
                            }
                        }
                    }
                )
            }
        }
        with(BookShareRoute.Aspetto) {
            composable(route) {
                AspettoScreen(themeState, onThemeSelected, navHostController = navController)
            }
        }
        with(BookShareRoute.Chronology) {
            composable(route) {
                val chronologyVm = koinViewModel<ChronologyBookViewModel>()
                val bookChronoState by chronologyVm.booksState.collectAsStateWithLifecycle()
                ChronologyBookScreen(navController = navController, list = bookChronoState)
            }
        }
        with(BookShareRoute.ChronologyDetails) {
            composable(route, arguments) { backStackEntry ->
                val detailsChronoVm = koinViewModel<ChronologyDetailsViewModel>()
                val bookState by detailsChronoVm.booksState.collectAsStateWithLifecycle()
                val idprestito = backStackEntry.arguments?.getInt("prestitoId")
                if (idprestito != null) {
                    detailsChronoVm.loadPrestitoDetails(idPrestito = idprestito)
                }
                ChronologyDetails(
                    navController = navController,
                    bookState,
                    onSubmit = { idPrestito, recensione, idLibro ->
                        detailsChronoVm.addRecensione(
                            idPrestito = idPrestito,
                            recensione = recensione,
                            idLibro = idLibro
                        )
                    })
            }
        }
        with(BookShareRoute.ModificaProfilo) {
            composable(route) {
                val modifyProfileVm = koinViewModel<ModificaProfiloViewModel>()
                val email by modifyProfileVm.email.collectAsStateWithLifecycle()
                ModificaProfiloScreen(email, navHostController = navController)
            }
        }
        with(BookShareRoute.ModificaPassword) {
            composable(route) {
                val modifyPassVm = koinViewModel<ModificaPasswordViewModel>()
                val state by modifyPassVm.state.collectAsStateWithLifecycle()
                ModificaPasswordScreen(
                    state = state,
                    action = modifyPassVm.actions,
                    onSubmit = { modifyPassVm.editPassword() },
                    navHostController = navController
                )
            }
        }
        with(BookShareRoute.ModificaEmail) {
            composable(route) {
                val modifyEmailVm = koinViewModel<ModificaEmailViewModel>()
                val state by modifyEmailVm.state.collectAsStateWithLifecycle()
                ModificaEmailScreen(
                    state = state,
                    action = modifyEmailVm.actions,
                    onSubmit = { modifyEmailVm.editEmail() },
                    navHostController = navController
                )
            }
        }
    }
}
