package com.example.elaboratomobile.data.database

import android.icu.text.SimpleDateFormat
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.ParseException
import java.util.Date
import java.util.Locale

@Database(
    entities = [Libro::class,
        Biblioteca::class,
        Utente::class,
        Evento::class,
        Interazione::class,
        LibroPrestito::class,
        LibroPosseduto::class,
        Piacere::class,
        Genere::class], version = 12
)
@TypeConverters(Converters::class)
abstract class ElaboratoMobileDatabase : RoomDatabase() {
    abstract fun libroDAO(): LibroDAO

    abstract fun bibliotecaDAO(): BibliotecaDAO

    abstract fun utenteDAO(): UtenteDAO

    abstract fun eventoDAO(): EventoDAO

    abstract fun interazioneDAO(): InterazioneDAO

    abstract fun libroPrestitoDAO(): LibroPrestitoDAO

    abstract fun libroPossedutoDAO(): LibroPossedutoDAO

    abstract fun piacereDAO(): PiacereDAO

    abstract fun genereDAO() : GenereDAO
}

class Converters {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    @TypeConverter
    fun fromStringToDate(value: String?): Date? {
        return value?.let {
            try {
                dateFormat.parse(it)
            } catch (e: ParseException) {
                null
            }
        }
    }

    @TypeConverter
    fun fromDateToString(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }
}