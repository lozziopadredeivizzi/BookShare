package com.example.elaboratomobile.ui.screens.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.NotificationRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

data class Notification(
    var titoloLibro: String,
    var nomeBiblioteca: String,
    var idPossesso: Int
)

class NotificViewModel (
    private val repository: NotificationRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {

   private val _notificState = MutableStateFlow<List<Notification>>(emptyList())
    val notificState = _notificState.asStateFlow()

    init {
        viewModelScope.launch {
            val username = usernameRepository.username.first()
            val today = Calendar.getInstance().time
            repository.getNotifics(username, today).collect{notifics->
                _notificState.value = notifics
            }
        }
    }

    fun update(idPossesso: Int) {
        viewModelScope.launch {
            repository.updateVisualizzato(idPossesso)
        }
    }
}