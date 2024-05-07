package com.example.elaboratomobile

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.elaboratomobile.data.database.ElaboratoMobileDatabase
import com.example.elaboratomobile.data.repositories.AspettoRepository
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.data.repositories.EventsRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import com.example.elaboratomobile.ui.screens.aspetto.AspettoViewModel
import com.example.elaboratomobile.ui.screens.events.EventsViewModel
import com.example.elaboratomobile.ui.screens.login.LoginViewModel
import com.example.elaboratomobile.ui.screens.profile.ProfileViewModel
import com.example.elaboratomobile.ui.screens.registrazione.RegistrazioneViewModel
import com.example.elaboratomobile.ui.screens.share.BooksViewModel
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

    single { UtenteRepository(get<ElaboratoMobileDatabase>().utenteDAO()) }

    single {
        BooksRepository(
            get<ElaboratoMobileDatabase>().libroDAO(),
            get<ElaboratoMobileDatabase>().piacereDAO(),
            get<ElaboratoMobileDatabase>().genereDAO()
        )
    }

    single { AspettoRepository(get()) }

    single{ EventsRepository(
        get<ElaboratoMobileDatabase>().eventoDAO()
    ) }

    viewModel { AspettoViewModel(get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { BooksViewModel(get(), get()) }

    viewModel { RegistrazioneViewModel(get(), get()) }

    viewModel { ProfileViewModel(get(), get()) }

    viewModel { EventsViewModel(get())}
}