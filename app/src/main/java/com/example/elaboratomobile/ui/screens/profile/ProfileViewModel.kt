package com.example.elaboratomobile.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.database.Utente
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class UsernameState(val username: String)

class ProfileViewModel(
    private val utenteRepo: UtenteRepository,
    private val usernameRepo: UsernameRepository
) : ViewModel() {
    var state by mutableStateOf(UsernameState(""))
        private set

    private val _num = MutableStateFlow<Int>(0)
    val num: StateFlow<Int> = _num.asStateFlow()

    private val _user = MutableStateFlow<Utente?>(null)
    val user: StateFlow<Utente?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            state = UsernameState(usernameRepo.username.first())

            launch {
                utenteRepo.getBookTotalNumber(state.username).collect { count ->
                    _num.value = count
                }
            }
            launch {
                utenteRepo.getFromUsername(state.username).collect { currentUser ->
                    _user.value = currentUser
                }
            }

        }
    }


}