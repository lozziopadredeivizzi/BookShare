package com.example.elaboratomobile.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Libro::class,
        Biblioteca::class,
        Utente::class,
        Evento::class,
        Interazione::class,
        LibroPrestito::class,
        LibroPosseduto::class], version = 1
)
abstract class ElaboratoMobileDatabase : RoomDatabase() {
    abstract fun libroDAO(): LibroDAO

    abstract fun bibliotecaDAO(): BibliotecaDAO

    abstract fun utenteDAO(): UtenteDAO

    abstract fun eventoDAO(): EventoDAO

    abstract fun interazioneDAO(): InterazioneDAO

    abstract fun libroPrestitoDAO(): LibroPrestitoDAO

    abstract fun libroPossedutoDAO(): LibroPossedutoDAO
}