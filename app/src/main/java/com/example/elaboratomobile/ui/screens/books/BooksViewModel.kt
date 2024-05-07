package com.example.elaboratomobile.ui.screens.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.database.Genere
import com.example.elaboratomobile.data.database.Libro
import com.example.elaboratomobile.data.database.Piacere
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//Classe che contiene i dati di libro e se gli è stato messo like dall'utente loggato
data class BookLike(
    val id_libro: Int,
    val titolo: String,
    val autore: String,
    val recensione: Double,
    val copertina: String,
    val trama: String,
    val id_genere: Int,
    val genereNome: String,
    val isLiked: Boolean
)

data class GeneriState(
    val generi: List<Genere>
)

class BooksViewModel(
    private val repository: BooksRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {

    private val _booksState = MutableStateFlow<List<BookLike>>(emptyList())
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

    fun loadBook() {
        viewModelScope.launch {
            val username = usernameRepository.username.first()
            repository.getAllBooks(username, selectedGenre.value).collect { booksLike ->
                _booksState.update { booksLike }
            }
        }
    }

    //Funzione che viene richiamata ogni volta che l'utente mette like a un libro,
    //dopo che è stata chiamata la query per aggiungere o eliminare l'associazione in piacere
    fun updateLikeStatus(bookId: Int) {
        viewModelScope.launch {
            val currentUsername = usernameRepository.username.first()

            val currentlyLiked = repository.isLiked(bookId, currentUsername).first()
            val piacere = Piacere(id_libro = bookId, currentUsername)
            if (currentlyLiked) {
                repository.delete(piacere)
            } else {
                repository.upsert(piacere)
            }

            repository.getAllBooks(currentUsername, selectedGenre.value).collect { booksLike ->
                _booksState.update { booksLike }
            }
        }

    }
}