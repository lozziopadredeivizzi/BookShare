package com.example.elaboratomobile.ui.screens.chronology

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import com.example.elaboratomobile.ui.screens.books.GeneriState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BookChrono(
    val id_libro: Int,
    val titolo: String,
    val autore: String,
    val recensione: Double,
    val copertina: String,
    val trama: String,
    val id_genere: Int,
    val genereNome: String,
    val idPrestito: Int
)

class ChronologyBookViewModel(
    private val repository: BooksRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {

    private val _booksState = MutableStateFlow<List<BookChrono>>(emptyList())
    val booksState = _booksState.asStateFlow()

    // Stato per tenere traccia del genere selezionato
    private val _selectedGenre = MutableStateFlow<Int>(0) // Per dire tutti i generi -> 0
    val selectedGenre = _selectedGenre.asStateFlow()

    val generiState = repository.generi.map { GeneriState(generi = it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GeneriState(emptyList())
    )

    // Metodo per impostare il genere selezionato dall'esterno
    fun setSelectedGenre(genreId: Int) {
        _selectedGenre.value = genreId
        loadBook() // Ricarica i libri in base al nuovo genere selezionato
    }

    init {
        loadBook()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadBook() {
        viewModelScope.launch {
            usernameRepository.username.flatMapLatest { username ->
                selectedGenre.flatMapLatest { genreId ->
                    repository.getChronologyBooksByUser(username, genreId)
                }
            }.collect { booksChrono ->
                _booksState.value = booksChrono
            }
        }
    }

}