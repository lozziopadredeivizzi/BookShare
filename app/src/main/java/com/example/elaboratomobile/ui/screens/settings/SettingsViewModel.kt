package com.example.elaboratomobile.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.database.Utente
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.ui.screens.login.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val usernameRepository: UsernameRepository) : ViewModel() {

    fun logOut(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            usernameRepository.setCurrentUsername("")
            // Attendi che l'operazione di logout sia completata prima di chiamare il callback
            // Questo garantisce che il callback verrà chiamato solo dopo che l'operazione è stata completata
            callback(true)
        }
    }
}