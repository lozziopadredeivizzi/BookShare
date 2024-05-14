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

class NotificationViewModel (
    private val repository: NotificationRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {

    private val _unreadNotificationsCount = MutableStateFlow(0)
    val unreadNotificationsCount = _unreadNotificationsCount.asStateFlow()

    fun updataNotification() {
        viewModelScope.launch {
            var username = usernameRepository.username.first()
            val today = Calendar.getInstance().time
            _unreadNotificationsCount.value = repository.getCountNotification(username, today)
        }
    }
}