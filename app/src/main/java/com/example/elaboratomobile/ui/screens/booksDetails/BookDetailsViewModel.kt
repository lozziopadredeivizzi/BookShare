package com.example.elaboratomobile.ui.screens.booksDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elaboratomobile.data.database.LibroPrestito
import com.example.elaboratomobile.data.repositories.BooksRepository
import com.example.elaboratomobile.data.repositories.UsernameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

data class BooKGenere(
    val id_libro: Int,
    val titolo: String,
    val autore: String,
    val recensione: Double,
    val copertina: String,
    val trama: String,
    val id_genere: Int,
    val genereNome: String,
)

data class PossessoState(
    val idPossesso: Int,
    val id_biblioteca: Int,
    val nome: String,
    val indirizzo: String,
    val citta: String
)

class BookDetailsViewModel(
    private val repository: BooksRepository,
    private val usernameRepository: UsernameRepository
) : ViewModel() {

    private val _booksState = MutableStateFlow<BooKGenere?>(null)
    val booksState = _booksState.asStateFlow()

    private val _librariesState = MutableStateFlow<List<PossessoState>>(emptyList())
    val librariesState = _librariesState.asStateFlow()

    private val _recensioniForLibro = MutableStateFlow<List<Pair<Int, Int>>>(emptyList())
    val recensioniForLibro = _recensioniForLibro.asStateFlow()

    // Funzione si può richiamare tutte le volte che finiamo nella rotta dei dettagli passandogli l'idLibro
    fun loadBookAndLibraries(id_libro: Int) {
        viewModelScope.launch {

            // Lanciare una nuova coroutine per il primo collect
            launch {
                repository.getLibrariesWhitFreeBook(id_libro).collect { libraries ->
                    _librariesState.value = libraries
                }
            }
            // Lanciare un'altra coroutine per il secondo collect
            launch {
                repository.getBookFromID(id_libro).collect { book ->
                    _booksState.value = book
                }
            }
            launch {
                val recensioni = repository.getRecensioniForLibro(id_libro)
                _recensioniForLibro.value = recensioni
            }

        }
    }

    fun addPrestito(id_possesso: Int) {
        viewModelScope.launch {
            repository.updateStatoPrenotazione(id_possesso)

            val dataInizio = Date() // Data corrente
            val calendar = Calendar.getInstance().apply {
                time = dataInizio
                add(Calendar.DAY_OF_MONTH, 30) // Aggiunge 30 giorni alla data corrente
            }
            val dataFine = calendar.time

            val newPrestito = LibroPrestito(
                id_possesso = id_possesso,
                username = usernameRepository.username.first(),
                data_inizio = dataInizio,
                data_fine = dataFine,
                recensione = null
            )
            repository.upsert(newPrestito)
        }
    }
}