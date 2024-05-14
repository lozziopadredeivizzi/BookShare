package com.example.elaboratomobile.data.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.io.ByteArrayOutputStream
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
        Genere::class], version = 14
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

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null || byteArray.isEmpty()) {
            return null // o restituisci un bitmap predefinito, o solleva un'eccezione, a seconda dei requisiti
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}