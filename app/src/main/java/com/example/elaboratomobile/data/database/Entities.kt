package com.example.elaboratomobile.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "BIBLIOTECA")
data class Biblioteca(
    @PrimaryKey(autoGenerate = true)
    val id_biblioteca: Int,

    @ColumnInfo
    var nome: String,

    @ColumnInfo
    var indirizzo: String,

    @ColumnInfo
    var citta: String
)

@Entity(
    tableName = "EVENTO",
    foreignKeys = [
        ForeignKey(
            entity = Biblioteca::class,
            parentColumns = arrayOf("id_biblioteca"),
            childColumns = arrayOf("id_biblioteca"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Evento(
    @PrimaryKey(autoGenerate = true)
    val id_evento: Int,

    @ColumnInfo
    var titolo: String,

    @ColumnInfo
    var dataOra_evento: String,

    @ColumnInfo
    var aula: String?,

    @ColumnInfo
    var descrizione: String,

    @ColumnInfo
    var id_biblioteca: Int
)

@Entity(
    tableName = "INTERAZIONE",
    primaryKeys = ["username", "id_evento"],
    foreignKeys = [
        ForeignKey(
            entity = Utente::class,
            parentColumns = arrayOf("username"),
            childColumns = arrayOf("username"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Evento::class,
            parentColumns = arrayOf("id_evento"),
            childColumns = arrayOf("id_evento"),
            onDelete = ForeignKey.CASCADE
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
            childColumns = ["username"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["id_libro"],
            childColumns = ["id_libro"],
            onDelete = ForeignKey.CASCADE
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
    var immagineProfilo: String,

    @ColumnInfo
    var data_nascita: String // Data 
)


@Entity(tableName = "LIBRO")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    val id_libro: Int,

    @ColumnInfo
    var titolo: String,

    @ColumnInfo
    var autore: String,

    @ColumnInfo
    var genere: String,

    @ColumnInfo
    var recensione: Double,

    @ColumnInfo
    var copertina: String,

    @ColumnInfo
    var trama: String
)

@Entity(
    tableName = "LIBRO_POSSEDUTO",
    foreignKeys = [
        ForeignKey(
            entity = Libro::class,
            parentColumns = arrayOf("id_libro"),
            childColumns = arrayOf("id_libro"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Biblioteca::class,
            parentColumns = arrayOf("id_biblioteca"),
            childColumns = arrayOf("id_biblioteca"),
            onDelete = ForeignKey.CASCADE
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
            childColumns = arrayOf("username"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LibroPosseduto::class,
            parentColumns = arrayOf("id_possesso"),
            childColumns = arrayOf("id_possesso"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LibroPrestito(
    var username: String,
    var id_possesso: Int,
    var data_inizio: String,

    @ColumnInfo
    var data_fine: String,

    @ColumnInfo
    var recensione: Int?
)