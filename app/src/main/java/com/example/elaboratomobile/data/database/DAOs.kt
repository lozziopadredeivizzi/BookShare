package com.example.elaboratomobile.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface LibroDAO {
    @Upsert
    suspend fun upsert(place: Libro)

    @Delete
    suspend fun delete(item: Libro)
}

@Dao
interface BibliotecaDAO {
    @Upsert
    suspend fun upsert(place: Biblioteca)

    @Delete
    suspend fun delete(item: Biblioteca)
}

@Dao
interface UtenteDAO {
    @Upsert
    suspend fun upsert(place: Utente)

    @Delete
    suspend fun delete(item: Utente)
}

@Dao
interface EventoDAO {
    @Upsert
    suspend fun upsert(place: Evento)

    @Delete
    suspend fun delete(item: Evento)
}

@Dao
interface InterazioneDAO {
    @Upsert
    suspend fun upsert(place: Interazione)

    @Delete
    suspend fun delete(item: Interazione)
}

@Dao
interface LibroPrestitoDAO {
    @Upsert
    suspend fun upsert(place: LibroPrestito)

    @Delete
    suspend fun delete(item: LibroPrestito)
}

@Dao
interface LibroPossedutoDAO {
    @Upsert
    suspend fun upsert(place: LibroPosseduto)

    @Delete
    suspend fun delete(item: LibroPosseduto)
}