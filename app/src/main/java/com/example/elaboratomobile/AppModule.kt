package com.example.elaboratomobile

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.elaboratomobile.data.database.ElaboratoMobileDatabase
import com.example.elaboratomobile.data.repositories.AspettoRepository
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.data.repositories.EventsRepository
import com.example.elaboratomobile.data.repositories.NotificationRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import com.example.elaboratomobile.ui.screens.aspetto.AspettoViewModel
import com.example.elaboratomobile.ui.screens.books.BooksViewModel
import com.example.elaboratomobile.ui.screens.books.FavoriteBookViewModel
import com.example.elaboratomobile.ui.screens.booksDetails.BookDetailsViewModel
import com.example.elaboratomobile.ui.screens.chronology.ChronologyBookViewModel
import com.example.elaboratomobile.ui.screens.chronologyDetails.ChronologyDetailsViewModel
import com.example.elaboratomobile.ui.screens.events.EventsViewModel
import com.example.elaboratomobile.ui.screens.loading.LoadingViewModel
import com.example.elaboratomobile.ui.screens.login.LoginViewModel
import com.example.elaboratomobile.ui.screens.map.MapViewModel
import com.example.elaboratomobile.ui.screens.modificaEmail.ModificaEmailViewModel
import com.example.elaboratomobile.ui.screens.modificaPassword.ModificaPasswordViewModel
import com.example.elaboratomobile.ui.screens.modificaProfilo.ModificaProfiloViewModel
import com.example.elaboratomobile.ui.screens.notification.NotificViewModel
import com.example.elaboratomobile.ui.screens.notification.NotificationViewModel
import com.example.elaboratomobile.ui.screens.profile.ProfileViewModel
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneViewModel
import com.example.elaboratomobile.ui.screens.settings.SettingsViewModel
import com.example.elaboratomobile.utils.LocationService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.usernameDataStore by preferencesDataStore("currentUsername")
val Context.themeDataStore by preferencesDataStore("theme")


val appModule = module {
    single { get<Context>().usernameDataStore }

    single { get<Context>().themeDataStore }

    single {
        Room.databaseBuilder(
            get(),
            ElaboratoMobileDatabase::class.java,
            "book-share"
        ).createFromAsset("database/book-share.db").build()
        //.fallbackToDestructiveMigration() per cancellare il db e tenere solo il precompilato
    }

    single { UsernameRepository(get()) }

    single { LocationService(get()) }

    single { UtenteRepository(get<ElaboratoMobileDatabase>().utenteDAO()) }

    single {
        BooksRepository(
            get<ElaboratoMobileDatabase>().libroDAO(),
            get<ElaboratoMobileDatabase>().piacereDAO(),
            get<ElaboratoMobileDatabase>().genereDAO(),
            get<ElaboratoMobileDatabase>().bibliotecaDAO(),
            get<ElaboratoMobileDatabase>().libroPossedutoDAO(),
            get<ElaboratoMobileDatabase>().libroPrestitoDAO()
        )
    }

    single { AspettoRepository(get()) }

    single {
        EventsRepository(
            get<ElaboratoMobileDatabase>().eventoDAO()
        )
    }

    single { NotificationRepository(get<ElaboratoMobileDatabase>().libroPrestitoDAO()) }

    viewModel { AspettoViewModel(get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { BooksViewModel(get(), get()) }

    viewModel { RegistrazioneViewModel(get(), get()) }

    viewModel { ProfileViewModel(get(), get()) }

    viewModel { EventsViewModel(get()) }

    viewModel { FavoriteBookViewModel(get(), get()) }

    viewModel { BookDetailsViewModel(get(), get()) }

    viewModel { ChronologyBookViewModel(get(), get()) }

    viewModel { ChronologyDetailsViewModel(get()) }

    viewModel { ModificaProfiloViewModel(get(), get()) }

    viewModel { ModificaEmailViewModel(get(), get()) }

    viewModel { ModificaPasswordViewModel(get(), get()) }

    viewModel { SettingsViewModel(get()) }

    viewModel { LoadingViewModel(get()) }

    viewModel { MapViewModel() }

    viewModel { NotificationViewModel(get(), get())}

    viewModel { NotificViewModel(get(), get())}
}