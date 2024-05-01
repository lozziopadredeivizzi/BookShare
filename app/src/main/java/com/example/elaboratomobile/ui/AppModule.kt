package com.example.elaboratomobile.ui

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.elaboratomobile.data.repositories.AspettoRepository
import com.example.elaboratomobile.ui.screens.aspetto.AspettoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("theme")

val appModule = module {
    single { get<Context>().dataStore }

    single { AspettoRepository(get()) }

    viewModel { AspettoViewModel(get()) }
}