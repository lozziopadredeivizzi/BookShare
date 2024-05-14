package com.example.elaboratomobile.data.database

import android.graphics.Bitmap
import android.icu.text.AlphabeticIndex.ImmutableIndex
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.elaboratomobile.ui.screens.chronology.BookChrono
import com.example.elaboratomobile.ui.screens.events.EventState
import com.example.elaboratomobile.ui.screens.books.BookLike
import com.example.elaboratomobile.ui.screens.booksDetails.BooKGenere
import com.example.elaboratomobile.ui.screens.booksDetails.PossessoState
import com.example.elaboratomobile.ui.screens.chronologyDetails.BookPrestito
import com.example.elaboratomobile.ui.screens.notification.Notification
import kotlinx.coroutines.flow.Flow
import java.util.Date

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

    @Query("""
        SELECT l.*, g.nome as genereNome, lp.id_possesso as idPrestito
        FROM LIBRO_PRESTITO lp
        INNER JOIN LIBRO_POSSEDUTO lpos ON lp.id_possesso = lpos.id_possesso
        INNER JOIN LIBRO l ON lpos.id_libro = l.id_libro
        INNER JOIN GENERE g ON l.id_genere = g.id_genere
        WHERE lp.username = :username AND (l.id_genere = :idGenere OR :idGenere = 0)
    """)
    fun getChronologyBooksByUser(username: String, idGenere: Int): Flow<List<BookChrono>>

    @Query("""
        WITH AverageRating AS (
            SELECT AVG(LP.recensione) as mediaRecensione
            FROM LIBRO_PRESTITO LP
            JOIN LIBRO_POSSEDUTO LPOS ON LP.id_possesso = LPOS.id_possesso
            WHERE LPOS.id_libro = :idLibro
        )
        UPDATE LIBRO
        SET recensione = (SELECT mediaRecensione FROM AverageRating)
        WHERE id_libro = :idLibro;
    """)
    suspend fun updateLibroRecensioneMedia(idLibro: Int)

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

    @Query("UPDATE UTENTE SET e_mail = :email WHERE username = :username")
    fun editEmail(email: String, username: String)

    @Query("SELECT password FROM UTENTE WHERE username = :username")
    fun getCurrentPasswordFromUsername(username: String): String

    @Query("UPDATE UTENTE SET password = :password WHERE username = :username")
    fun editPassword(password: String, username: String)

    @Query("UPDATE UTENTE SET immagineProfilo = :immagineProfilo WHERE username = :username")
    suspend fun editPfp(immagineProfilo: Bitmap, username: String)
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

    @Query("SELECT recensione FROM LIBRO_PRESTITO WHERE id_possesso IN " +
            "(SELECT id_possesso FROM LIBRO_POSSEDUTO WHERE id_libro = :idLibro)")
    suspend fun getRecensioniForBook(idLibro: Int): List<Int?>

    @Query("""
        SELECT 
            LP.data_inizio, 
            LP.data_fine, 
            LP.recensione as recensionePrestito,
            LP.id_possesso as idPrestito,
            L.*, 
            G.nome as genereNome,
            B.nome as nomeBiblioteca,
            B.citta as cittaBiblioteca
        FROM LIBRO_PRESTITO LP
        INNER JOIN LIBRO_POSSEDUTO LP2 ON LP.id_possesso = LP2.id_possesso
        INNER JOIN LIBRO L ON LP2.id_libro = L.id_libro
        INNER JOIN BIBLIOTECA B ON LP2.id_biblioteca = B.id_biblioteca
        INNER JOIN GENERE G ON L.id_genere = G.id_genere
        WHERE LP.id_possesso = :idPossesso
    """)
    fun getDettagliPrestito(idPossesso: Int): Flow<BookPrestito?>

    @Query("UPDATE LIBRO_PRESTITO SET recensione = :recensione WHERE id_possesso = :idPossesso")
    suspend fun updateRecensione(idPossesso: Int, recensione: Int)

    @Query("SELECT COUNT(*) FROM LIBRO_PRESTITO WHERE username = :username AND data_fine = :today AND visualizzato = 'false'")
    suspend fun getCountLibriPrestitiNonVisualizzati(username: String, today: Date): Int

    @Query("SELECT LIBRO.titolo AS titoloLibro, BIBLIOTECA.nome AS nomeBiblioteca, LIBRO_PRESTITO.id_possesso AS idPossesso " +
            "FROM LIBRO " +
            "INNER JOIN LIBRO_POSSEDUTO ON LIBRO.id_libro = LIBRO_POSSEDUTO.id_libro " +
            "INNER JOIN BIBLIOTECA ON LIBRO_POSSEDUTO.id_biblioteca = BIBLIOTECA.id_biblioteca " +
            "INNER JOIN LIBRO_PRESTITO ON LIBRO_POSSEDUTO.id_possesso = LIBRO_PRESTITO.id_possesso " +
            "WHERE LIBRO_PRESTITO.username = :username " +
            "AND LIBRO_PRESTITO.data_fine = :today")
    fun getNotificationsDetails(username: String, today: Date) :Flow<List<Notification>>

    @Query("UPDATE LIBRO_PRESTITO SET visualizzato = 'true' WHERE id_possesso = :idPossesso")
    suspend fun updateVisualizzatoForIdPossesso(idPossesso: Int)
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