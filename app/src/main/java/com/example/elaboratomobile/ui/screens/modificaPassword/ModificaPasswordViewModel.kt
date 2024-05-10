package com.example.elaboratomobile.ui.screens.modificaPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class EditPasswordState(
    var newPassword: String = "",
    var oldPassword: String = "",
    var message: String? = null,
    var success: Boolean? = null
) {
    val canSubmit get() = newPassword.isNotBlank() && oldPassword.isNotBlank()
}

interface EditPasswordAction {
    fun setNewPassword(password: String)
    fun setOldPassword(password: String)
}

class ModificaPasswordViewModel(
    val usernameRepo: UsernameRepository,
    val userRepo: UtenteRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EditPasswordState())
    val state = _state.asStateFlow()

    private var username by mutableStateOf<String>("")

    val actions = object : EditPasswordAction {
        override fun setNewPassword(password: String) {
            _state.update { it.copy(newPassword = password, message = null) }
        }

        override fun setOldPassword(password: String) {
            _state.update { it.copy(oldPassword = password, message = null) }
        }
    }

    init {
        viewModelScope.launch {
            username = usernameRepo.username.first()
        }
    }

    fun editPassword() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (userRepo.getCurrentPasswordFromUsername(username) == _state.value.oldPassword) {
                    _state.value.success = true
                    userRepo.editPassword(_state.value.newPassword, username)

                } else {
                    _state.update {
                        it.copy(
                            success = false,
                            message = "La password inserita non corrisponde. Riprova."
                        )
                    }

                }
            }
        }
    }

}