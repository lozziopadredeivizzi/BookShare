package com.example.elaboratomobile.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDAO {

    @Query("SELECT * FROM libro")
    fun getAll(): Flow<List<Libro>>
    @Upsert
    suspend fun upsert(book: Libro)

    @Delete
    suspend fun delete(item: Libro)

    @Query("SELECT EXISTS(SELECT 1 FROM PIACERE WHERE id_libro = :idLibro AND username = :username)")
    fun isLikedByUser(idLibro: Int, username: String): Flow<Boolean>
}

@Dao
interface GenereDAO {
    @Upsert
    suspend fun upsert(genere: Genere)

    @Delete
    suspend fun delete(genere: Genere)

    @Query("SELECT * FROM GENERE WHERE id_genere = :idGenere")
    suspend fun getGenere(idGenere: Int): Genere
}

@Dao
interface BibliotecaDAO {
    @Upsert
    suspend fun upsert(library: Biblioteca)

    @Delete
    suspend fun delete(item: Biblioteca)
}

@Dao
interface UtenteDAO {
    @Upsert
    suspend fun upsert(user: Utente)

    @Delete
    suspend fun delete(item: Utente)

    @Query("SELECT * FROM UTENTE WHERE username = :username AND password = :password")
    suspend fun checkLogin(username: String, password: String): Utente?

    @Query("SELECT * FROM UTENTE WHERE username = :username")
    suspend fun checkUsername(username: String): Utente?

    @Query("SELECT * FROM UTENTE WHERE username = :username")
    fun getFromUsername(username: String): Flow<Utente?>

    @Query("SELECT COUNT(*) as count FROM LIBRO_PRESTITO WHERE username = :username")
    fun getBookTotalNumber(username: String) : Flow<Int>
}

@Dao
interface EventoDAO {
    @Upsert
    suspend fun upsert(event: Evento)

    @Delete
    suspend fun delete(item: Evento)
}

@Dao
interface InterazioneDAO {
    @Upsert
    suspend fun upsert(interaction: Interazione)

    @Delete
    suspend fun delete(item: Interazione)
}

@Dao
interface LibroPrestitoDAO {
    @Upsert
    suspend fun upsert(bookLoan: LibroPrestito)

    @Delete
    suspend fun delete(item: LibroPrestito)
}

@Dao
interface LibroPossedutoDAO {
    @Upsert
    suspend fun upsert(bookOwned: LibroPosseduto)

    @Delete
    suspend fun delete(item: LibroPosseduto)
}

@Dao
interface PiacereDAO {
    @Upsert
    suspend fun upsert(like: Piacere)

    @Delete
    suspend fun delete(item: Piacere)

}