package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.Genere
import com.example.elaboratomobile.data.database.GenereDAO
import com.example.elaboratomobile.data.database.Libro
import com.example.elaboratomobile.data.database.LibroDAO
import com.example.elaboratomobile.data.database.Piacere
import com.example.elaboratomobile.data.database.PiacereDAO
import com.example.elaboratomobile.ui.screens.books.BookLike
import kotlinx.coroutines.flow.Flow

class BooksRepository(
    private val libroDAO: LibroDAO,
    private val piacereDAO: PiacereDAO,
    private val genereDAO: GenereDAO
) {

    fun getAllBooks(username: String, idGenere: Int): Flow<List<BookLike>> =
        libroDAO.getBooksAndLikesByGenere(idGenere, username)

    fun getAllFavoriteBooks(username: String, idGenere: Int) : Flow<List<BookLike>> =
        libroDAO.getLikedBooksByGenere(idGenere, username)

    suspend fun upsert(book: Libro) = libroDAO.upsert(book)

    suspend fun delete(item: Libro) = libroDAO.delete(item)

    fun isLiked(idLibro: Int, username: String): Flow<Boolean> =
        libroDAO.isLikedByUser(idLibro, username)

    suspend fun upsert(like: Piacere) = piacereDAO.upsert(like)

    suspend fun delete(like: Piacere) = piacereDAO.delete(like)


    val generi: Flow<List<Genere>> = genereDAO.getAll()
}