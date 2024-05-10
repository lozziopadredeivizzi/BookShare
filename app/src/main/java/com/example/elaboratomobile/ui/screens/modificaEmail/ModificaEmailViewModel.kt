package com.example.elaboratomobile.ui.screens.modificaEmail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import com.example.elaboratomobile.ui.screens.profile.UsernameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class EditEmailState(
    var email: String
){
    val canSubmit get() = email.isNotBlank()
}

interface EditEmailAction{
    fun setEmail(email: String)
}

class ModificaEmailViewModel(
    private val usernameRepo: UsernameRepository,
    private val userRepo: UtenteRepository
): ViewModel(){
    private var username by mutableStateOf<String>("")

    private val _state = MutableStateFlow(EditEmailState(""))
    val state = _state.asStateFlow()

    val actions = object : EditEmailAction{
        override fun setEmail(email: String) {
            _state.update { it.copy(email = email) }
        }
    }

    init {
        viewModelScope.launch {
            username = usernameRepo.username.first()
        }
    }
    init {
        viewModelScope.launch {
            userRepo.getFromUsername(username).collect{currentUser->
                if (currentUser != null) {
                    _state.value.email = currentUser.e_mail
                }
            }
        }
    }

    fun editEmail(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepo.editEmail(_state.value.email, username)
            }
        }
    }
}