package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.BibliotecaDAO
import com.example.elaboratomobile.data.database.Genere
import com.example.elaboratomobile.data.database.GenereDAO
import com.example.elaboratomobile.data.database.Libro
import com.example.elaboratomobile.data.database.LibroDAO
import com.example.elaboratomobile.data.database.LibroPossedutoDAO
import com.example.elaboratomobile.data.database.LibroPrestito
import com.example.elaboratomobile.data.database.LibroPrestitoDAO
import com.example.elaboratomobile.data.database.Piacere
import com.example.elaboratomobile.data.database.PiacereDAO
import com.example.elaboratomobile.ui.screens.chronology.BookChrono
import com.example.elaboratomobile.ui.screens.books.BookLike
import com.example.elaboratomobile.ui.screens.booksDetails.BooKGenere
import com.example.elaboratomobile.ui.screens.booksDetails.PossessoState
import com.example.elaboratomobile.ui.screens.chronologyDetails.BookPrestito
import kotlinx.coroutines.flow.Flow

class BooksRepository(
    private val libroDAO: LibroDAO,
    private val piacereDAO: PiacereDAO,
    private val genereDAO: GenereDAO,
    private val bibliotecaDAO: BibliotecaDAO,
    private val libroPossedutoDAO: LibroPossedutoDAO,
    private val libroPrestitoDAO: LibroPrestitoDAO,
) {

    fun getAllBooks(username: String, idGenere: Int): Flow<List<BookLike>> =
        libroDAO.getBooksAndLikesByGenere(idGenere, username)

    fun getAllFavoriteBooks(username: String, idGenere: Int): Flow<List<BookLike>> =
        libroDAO.getLikedBooksByGenere(idGenere, username)

    fun getBookFromID(idLibro: Int): Flow<BooKGenere> = libroDAO.getBookById(idLibro)

    fun getLibrariesWhitFreeBook(idLibro: Int): Flow<List<PossessoState>> =
        bibliotecaDAO.getLibrariesWithFreeBook(idLibro)

    suspend fun upsert(book: Libro) = libroDAO.upsert(book)

    suspend fun delete(item: Libro) = libroDAO.delete(item)

    fun isLiked(idLibro: Int, username: String): Flow<Boolean> =
        libroDAO.isLikedByUser(idLibro, username)

    suspend fun upsert(like: Piacere) = piacereDAO.upsert(like)

    suspend fun delete(like: Piacere) = piacereDAO.delete(like)

    val generi: Flow<List<Genere>> = genereDAO.getAll()

    suspend fun updateStatoPrenotazione(id_possesso: Int) =
        libroPossedutoDAO.updateStatoPrenotazione(id_possesso)

    suspend fun upsert(prestito: LibroPrestito) = libroPrestitoDAO.upsert(prestito)

    fun getChronologyBooksByUser(username: String, idGenere: Int): Flow<List<BookChrono>> =
        libroDAO.getChronologyBooksByUser(username, idGenere)

    fun getChronologyDetails(idPrestito: Int): Flow<BookPrestito?> =
        libroPrestitoDAO.getDettagliPrestito(idPrestito)

    suspend fun updateRecensionePrestito(idPrestito: Int, recensione: Int) =
        libroPrestitoDAO.updateRecensione(idPrestito, recensione)

    suspend fun updateLibroRecensioneMedia(idLibro: Int) =
        libroDAO.updateLibroRecensioneMedia(idLibro)

    suspend fun getRecensioniForLibro(idLibro: Int): List<Pair<Int, Int>> {
        val recensioni = libroPrestitoDAO.getRecensioniForBook(idLibro)
        val recensioniAggregated = recensioni.groupingBy { it?: 0 }
            .eachCount()
            .toList()
        // Aggiungi manualmente le coppie mancanti per le stelle da 1 a 5
        val recensioniComplete = mutableListOf<Pair<Int, Int>>()
        for (i in 1..5) {
            val recensioniStella = recensioniAggregated.find { it.first == i }
            if (recensioniStella != null) {
                recensioniComplete.add(recensioniStella)
            } else {
                recensioniComplete.add(Pair(i, 0))
            }
        }

        return recensioniComplete
    }
}