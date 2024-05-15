package com.example.elaboratomobile.data.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "BIBLIOTECA")
data class Biblioteca(
    @PrimaryKey(autoGenerate = true)
    val id_biblioteca: Int,

    @ColumnInfo
    var nome: String,

    @ColumnInfo
    var indirizzo: String,

    @ColumnInfo
    var citta: String,

    var latitudine: Double?,

    var longitudine: Double?

)

@Entity(
    tableName = "EVENTO",
    foreignKeys = [
        ForeignKey(
            entity = Biblioteca::class,
            parentColumns = arrayOf("id_biblioteca"),
            childColumns = arrayOf("id_biblioteca")
        )
    ]
)
data class Evento(
    @PrimaryKey(autoGenerate = true)
    val id_evento: Int,

    @ColumnInfo
    var titolo: String,

    @ColumnInfo
    var data: String,

    @ColumnInfo
    var ora: String,

    @ColumnInfo
    var aula: String?,

    @ColumnInfo
    var descrizione: String,

    var id_biblioteca: Int
)

@Entity(
    tableName = "INTERAZIONE",
    primaryKeys = ["username", "id_evento"],
    foreignKeys = [
        ForeignKey(
            entity = Utente::class,
            parentColumns = arrayOf("username"),
            childColumns = arrayOf("username")
        ),
        ForeignKey(
            entity = Evento::class,
            parentColumns = arrayOf("id_evento"),
            childColumns = arrayOf("id_evento")
        )
    ]
)

data class Interazione(
    var username: String,
    var id_evento: Int
)

@Entity(
    tableName = "PIACERE",
    primaryKeys = ["id_libro", "username"],
    foreignKeys = [
        ForeignKey(
            entity = Utente::class,
            parentColumns = ["username"],
            childColumns = ["username"]
        ),
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["id_libro"],
            childColumns = ["id_libro"]
        )
    ]
)
data class Piacere(
    val id_libro: Int,
    val username: String
)

@Entity(tableName = "UTENTE")
data class Utente(
    @PrimaryKey
    var username: String,

    @ColumnInfo
    var password: String,

    @ColumnInfo
    var nome: String,

    @ColumnInfo
    var cognome: String,

    @ColumnInfo
    var e_mail: String,

    @ColumnInfo
    var immagineProfilo: Bitmap?,

    @ColumnInfo
    var data_nascita: String // Data
)


@Entity(
    tableName = "LIBRO",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Genere::class,
            parentColumns = kotlin.arrayOf("id_genere"),
            childColumns = kotlin.arrayOf("id_genere")
        )
    ]
)
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id_libro: Int,

    @ColumnInfo
    var titolo: String,

    @ColumnInfo
    var autore: String,

    val id_genere: Int,

    @ColumnInfo
    var recensione: Double,

    @ColumnInfo
    var copertina: Bitmap?,

    @ColumnInfo
    var trama: String
)

@Entity(tableName = "GENERE")
data class Genere(
    @PrimaryKey(autoGenerate = true)
    val id_genere: Int,
    @ColumnInfo
    var nome: String
)

@Entity(
    tableName = "LIBRO_POSSEDUTO",
    foreignKeys = [
        ForeignKey(
            entity = Libro::class,
            parentColumns = arrayOf("id_libro"),
            childColumns = arrayOf("id_libro")
        ),
        ForeignKey(
            entity = Biblioteca::class,
            parentColumns = arrayOf("id_biblioteca"),
            childColumns = arrayOf("id_biblioteca")
        )
    ]
)
data class LibroPosseduto(
    @PrimaryKey(autoGenerate = true)
    val id_possesso: Int,

    @ColumnInfo
    var statoPrenotazione: String,

    @ColumnInfo
    var id_biblioteca: Int,

    @ColumnInfo
    var id_libro: Int
)

@Entity(
    tableName = "LIBRO_PRESTITO",
    primaryKeys = ["username", "id_possesso", "data_inizio"],
    foreignKeys = [
        ForeignKey(
            entity = Utente::class,
            parentColumns = arrayOf("username"),
            childColumns = arrayOf("username")
        ),
        ForeignKey(
            entity = LibroPosseduto::class,
            parentColumns = arrayOf("id_possesso"),
            childColumns = arrayOf("id_possesso")
        )
    ]
)
data class LibroPrestito(
    var username: String,
    var id_possesso: Int,
    var data_inizio: Date,

    @ColumnInfo
    var data_fine: Date,

    @ColumnInfo
    var recensione: Int?,

    var visualizzato: String
)