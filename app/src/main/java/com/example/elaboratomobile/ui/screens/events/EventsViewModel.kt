package com.example.elaboratomobile.ui.screens.events

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EventState(
    var titolo: String,
    var id_evento: Int,
    var data: String,
    var ora: String,
    var aula: String?,
    var descrizione: String,
    var id_biblioteca: Int,
    val nomeBiblioteca: String,
    val indirizzoBiblio: String,
    val immagineBiblio: Bitmap?
)

class EventsViewModel(
    private val repository: EventsRepository
) : ViewModel() {

    private val _eventsState = MutableStateFlow<List<EventState>>(emptyList())
    val eventsState = _eventsState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.events.collect{ eventsList->
                _eventsState.value = eventsList
            }
        }
    }

    fun editBiblio (immagine: Bitmap, id: Int) {
        viewModelScope.launch {
            repository.updateBiblio(immagine, id)
        }
    }
}



