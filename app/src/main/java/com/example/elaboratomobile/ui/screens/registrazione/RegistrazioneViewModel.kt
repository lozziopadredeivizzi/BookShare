package com.example.elaboratomobile.ui.screens.registrazione

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.database.Utente
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddUserState(
    val username: String = "",
    val password: String = "",
    val nome: String = "",
    val cognome: String = "",
    val email: String = "",
    val data_nascita: String = "",
    val immagineProfilo: String = "",
    val signUpSuccess: Boolean? = null,
    val errorMessage: String? = null
) {
    val canSubmit
        get() = username.isNotBlank() && password.isNotBlank() && nome.isNotBlank() && cognome.isNotBlank() &&
                email.isNotBlank() && data_nascita.isNotBlank()

    fun toUser() = Utente(
        nome = nome,
        cognome = cognome,
        password = password,
        e_mail = email,
        data_nascita = data_nascita,
        immagineProfilo = immagineProfilo,
        username = username
    )
}

interface AddUserActions {
    fun setUsername(title: String)
    fun setDate(date: String)
    fun setPassword(description: String)
    fun setPfp(immagineProfilo: String)
    fun setName(nome: String)
    fun setSurname(cognome: String)
    fun setEmail(email: String)
}

class RegistrazioneViewModel(
    private val utenteRepository: UtenteRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddUserState())
    val state = _state.asStateFlow()

    val actions = object : AddUserActions {
        override fun setUsername(username: String) =
            _state.update { it.copy(username = username, errorMessage = null) }

        override fun setDate(date: String) =
            _state.update { it.copy(data_nascita = date, errorMessage = null) }

        override fun setPassword(password: String) =
            _state.update { it.copy(password = password, errorMessage = null) }

        override fun setPfp(immagineProfilo: String) =
            _state.update { it.copy(immagineProfilo = immagineProfilo, errorMessage = null) }

        override fun setName(nome: String) =
            _state.update { it.copy(nome = nome, errorMessage = null) }

        override fun setSurname(cognome: String) =
            _state.update { it.copy(cognome = cognome, errorMessage = null) }

        override fun setEmail(email: String) =
            _state.update { it.copy(email = email, errorMessage = null) }
    }

    fun signUp() {
        viewModelScope.launch {
            val utente = utenteRepository.checkUsername(_state.value.username)
            if (utente == null) {
                usernameRepository.setCurrentUsername(_state.value.username)
                _state.update { it.copy(signUpSuccess = true) }
                utenteRepository.addUser(_state.value.toUser())
            } else {
                _state.update {
                    it.copy(
                        signUpSuccess = false,
                        errorMessage = "Questo Username esiste già. Inserisci un altro Username."
                    )
                }
            }
        }
    }
}
