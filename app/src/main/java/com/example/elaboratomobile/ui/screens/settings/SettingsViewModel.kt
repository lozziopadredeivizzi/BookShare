package com.example.elaboratomobile.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.data.repositories.UtenteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val usernameRepository: UsernameRepository,
    private val utenteRepository: UtenteRepository
) : ViewModel() {

    private val _hasBiometric = MutableStateFlow(false)
    val hasBiometric = _hasBiometric.asStateFlow()

    private val _anyone = MutableStateFlow(false)
    val anyone = _anyone.asStateFlow()

    fun logOut(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            usernameRepository.setCurrentUsername("")
            // Attendi che l'operazione di logout sia completata prima di chiamare il callback
            // Questo garantisce che il callback verrà chiamato solo dopo che l'operazione è stata completata
            callback(true)
        }
    }

    init {
        viewModelScope.launch {
            val username = usernameRepository.username.first()
            launch {
                _hasBiometric.value = utenteRepository.hasBiometric(username)
            }
            launch {
                _anyone.value = !utenteRepository.fingerPrintAlreadyUsed()
            }
        }
    }

    fun addBiometric() {
        viewModelScope.launch {
            val username = usernameRepository.username.first()
            utenteRepository.addBiometricId(username)
            launch {
                _hasBiometric.value = utenteRepository.hasBiometric(username)
            }
            launch {
                _anyone.value = !utenteRepository.fingerPrintAlreadyUsed()
            }
        }
    }

    fun removeBiometric() {
        viewModelScope.launch {
            val username = usernameRepository.username.first()
            utenteRepository.removeBiometricId(username)
            launch {
                _hasBiometric.value = utenteRepository.hasBiometric(username)
            }
            launch {
                _anyone.value = !utenteRepository.fingerPrintAlreadyUsed()
            }
        }
    }
}