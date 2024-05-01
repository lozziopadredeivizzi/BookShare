package com.example.elaboratomobile

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.elaboratomobile.data.database.ElaboratoMobileDatabase
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import com.example.elaboratomobile.ui.screens.login.LoginViewModel
import com.example.elaboratomobile.ui.screens.share.BooksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("currentUsername")

val appModule = module {
    single { get<Context>().dataStore }

    single {
        Room.databaseBuilder(
            get(),
            ElaboratoMobileDatabase::class.java,
            "book-share"
        ).build()
    }

    single { UsernameRepository(get()) }

    single { UtenteRepository(get<ElaboratoMobileDatabase>().utenteDAO()) }

    single { BooksRepository(get<ElaboratoMobileDatabase>().libroDAO()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { BooksViewModel(get(), get()) }
}