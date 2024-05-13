package com.example.elaboratomobile.ui.screens.loading

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.UsernameRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoadingViewModel(
    private val usernameRepository: UsernameRepository
) : ViewModel() {
    private val _currentUsername = mutableStateOf("")
    private val currentUsername: String
        get() = _currentUsername.value

    fun loading(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            _currentUsername.value = usernameRepository.username.first()
            if(currentUsername.isEmpty()) {
                callback(false)
            }
            else {
                callback(true)
            }
        }
    }
}
