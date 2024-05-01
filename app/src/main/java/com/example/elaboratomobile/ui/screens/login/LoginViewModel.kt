package com.example.elaboratomobile.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class LoginState(
    val username: String = "",
    val password: String = "",
    val loginSuccess: Boolean? = null, // true = success false= failure
    val errorMessage: String? = null
) {
    val canSubmit get() = username.isNotBlank() && password.isNotBlank()
}

interface LoginActions {
    fun setUsername(username: String)
    fun setPassword(password: String)
}

class LoginViewModel(
    private val utenteRepository: UtenteRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    val actions = object : LoginActions {
        override fun setUsername(username: String) {
            _state.update { it.copy(username = username, errorMessage = null) }
        }

        override fun setPassword(password: String) {
            _state.update { it.copy(password = password, errorMessage = null) }
        }
    }

    fun login() {
        viewModelScope.launch {
            val utente = utenteRepository.checkLogin(_state.value.username, _state.value.password)
            if (utente != null) {
                usernameRepository.setCurrentUsername(_state.value.username)
                _state.update { it.copy(loginSuccess = true) }
            } else {
                _state.update {
                    it.copy(
                        loginSuccess = false,
                        errorMessage = "Controllare le credenziali inserite."
                    )
                }
            }
        }
    }
}