package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.Libro
import com.example.elaboratomobile.data.database.LibroDAO
import kotlinx.coroutines.flow.Flow

class BooksRepository(private val libroDAO: LibroDAO) {

    val books: Flow<List<Libro>> = libroDAO.getAll()

    suspend fun upsert(book: Libro) = libroDAO.upsert(book)

    suspend fun delete(item: Libro) = libroDAO.delete(item)

    fun isLiked(idLibro: Int, username: String): Flow<Boolean> = libroDAO.isLikedByUser(idLibro, username)
}