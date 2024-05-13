package com.example.elaboratomobile.ui.screens.modificaProfilo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.database.Utente
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import com.example.elaboratomobile.ui.screens.profile.UsernameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ModificaProfiloViewModel(
    private val usernameRepo: UsernameRepository,
    private val utenteRepo: UtenteRepository
) : ViewModel() {
    private var username by mutableStateOf(UsernameState(""))

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email.asStateFlow()

    init {
        viewModelScope.launch {
            username = UsernameState(usernameRepo.username.first())

            launch {
                utenteRepo.getFromUsername(username.username).collect { currentUser ->
                    if (currentUser != null) {
                        _email.value = currentUser.e_mail
                    }
                    else{
                        _email.value = "/"
                    }
                }
            }
        }
    }
}