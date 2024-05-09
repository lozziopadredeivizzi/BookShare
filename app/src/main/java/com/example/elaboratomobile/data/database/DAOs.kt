package com.example.elaboratomobile.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.elaboratomobile.ui.screens.events.EventState
import com.example.elaboratomobile.ui.screens.books.BookLike
import com.example.elaboratomobile.ui.screens.booksDetails.BooKGenere
import com.example.elaboratomobile.ui.screens.booksDetails.PossessoState
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDAO {
    @Upsert
    suspend fun upsert(book: Libro)

    @Delete
    suspend fun delete(item: Libro)

    @Query(
        """
    SELECT L.*, G.nome as genereNome
    FROM LIBRO L
    JOIN GENERE G ON L.id_genere = G.id_genere
    WHERE L.id_libro = :idLibro
    """
    )
    fun getBookById(idLibro: Int): Flow<BooKGenere>

    @Query("SELECT EXISTS(SELECT 1 FROM PIACERE WHERE id_libro = :idLibro AND username = :username)")
    fun isLikedByUser(idLibro: Int, username: String): Flow<Boolean>

    @Query(
        """
    SELECT L.*, G.nome as genereNome, 
        EXISTS(SELECT 1 FROM PIACERE WHERE id_libro = L.id_libro AND username = :username) AS isLiked
    FROM LIBRO L
    JOIN GENERE G ON L.id_genere = G.id_genere
    WHERE L.id_genere = :idGenere OR :idGenere = 0
"""
    )
    fun getBooksAndLikesByGenere(idGenere: Int, username: String): Flow<List<BookLike>>

    @Query("""
    SELECT L.*, G.nome as genereNome, 
           EXISTS(SELECT 1 FROM PIACERE P WHERE P.id_libro = L.id_libro AND P.username = :username) AS isLiked
    FROM LIBRO L
    JOIN GENERE G ON L.id_genere = G.id_genere
    JOIN PIACERE P ON L.id_libro = P.id_libro
    WHERE (L.id_genere = :idGenere OR :idGenere = 0) AND P.username = :username
""")
    fun getLikedBooksByGenere(idGenere: Int, username: String): Flow<List<BookLike>>

}

@Dao
interface GenereDAO {
    @Upsert
    suspend fun upsert(genere: Genere)

    @Delete
    suspend fun delete(genere: Genere)

    @Query("SELECT * FROM GENERE WHERE id_genere = :idGenere")
    suspend fun getGenere(idGenere: Int): Genere

    @Query("SELECT * FROM GENERE")
    fun getAll(): Flow<List<Genere>>

}

@Dao
interface BibliotecaDAO {
    @Upsert
    suspend fun upsert(library: Biblioteca)

    @Delete
    suspend fun delete(item: Biblioteca)

    @Query("""
        SELECT b.*, lp.id_possesso as idPossesso
        FROM LIBRO_POSSEDUTO lp
        JOIN BIBLIOTECA b ON lp.id_biblioteca = b.id_biblioteca
        WHERE lp.id_libro = :idLibro AND lp.statoPrenotazione = 'Libero'
    """)
    fun getLibrariesWithFreeBook(idLibro: Int) : Flow<List<PossessoState>>
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
    fun getBookTotalNumber(username: String): Flow<Int>
}

@Dao
interface EventoDAO {
    @Upsert
    suspend fun upsert(event: Evento)

    @Delete
    suspend fun delete(item: Evento)

    @Query("SELECT e.*, b.nome AS nomeBiblioteca, b.indirizzo AS indirizzoBiblio FROM EVENTO e JOIN BIBLIOTECA b ON e.id_biblioteca= b.id_biblioteca ")
    fun getAll(): Flow<List<EventState>>
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

    @Query("UPDATE LIBRO_POSSEDUTO SET statoPrenotazione = 'Occupato' WHERE id_possesso = :idPossesso")
    suspend fun updateStatoPrenotazione(idPossesso: Int)
}

@Dao
interface PiacereDAO {
    @Upsert
    suspend fun upsert(like: Piacere)

    @Delete
    suspend fun delete(item: Piacere)

}