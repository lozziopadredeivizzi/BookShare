package com.example.elaboratomobile.ui.screens.chronologyDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.ui.screens.chronology.BookChrono
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

data class BookPrestito(
    val id_libro: Int,
    val titolo: String,
    val autore: String,
    val recensione: Double,
    val copertina: String,
    val trama: String,
    val id_genere: Int,
    val genereNome: String,
    val idPrestito: Int,
    val nomeBiblioteca: String,
    val cittaBiblioteca: String,
    val recensionePrestito: Int,
    val data_inizio: Date,
    val data_fine: Date,
)
class ChronologyDetailsViewModel(
    private val repository: BooksRepository
) : ViewModel()  {

    private val _booksState = MutableStateFlow<BookPrestito?>(null)
    val booksState = _booksState.asStateFlow()

    fun loadPrestitoDetails(idPrestito: Int) {
        viewModelScope.launch {

            launch {
                repository.getChronologyDetails(idPrestito).collect{bookPrestito->
                    _booksState.value = bookPrestito
                }
            }

        }
    }

    fun addRecensione(idPrestito: Int, recensione: Int, idLibro: Int) {
        viewModelScope.launch {
            repository.updateRecensionePrestito(idPrestito, recensione)

            repository.updateLibroRecensioneMedia(idLibro)
        }
    }
}
