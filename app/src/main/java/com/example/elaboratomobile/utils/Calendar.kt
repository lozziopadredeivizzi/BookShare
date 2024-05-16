package com.example.elaboratomobile.utils

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import com.example.elaboratomobile.ui.screens.events.EventState
import java.text.SimpleDateFormat
import java.util.Locale

fun aggiungiEventoAlCalendario(context: Context, evento: EventState) {
    val intent = Intent(Intent.ACTION_INSERT)
        .setData(CalendarContract.Events.CONTENT_URI)
        .putExtra(CalendarContract.Events.TITLE, evento.titolo)
        .putExtra(CalendarContract.Events.DESCRIPTION, evento.descrizione)
        .putExtra(CalendarContract.Events.EVENT_LOCATION, evento.indirizzoBiblio)
        .putExtra(
            CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            convertiDataOraInTimestamp(evento.data, evento.ora)
        )
        .putExtra(CalendarContract.Events.ALL_DAY, false)
        .putExtra(CalendarContract.Events.STATUS, CalendarContract.Events.STATUS_CONFIRMED)
    context.startActivity(intent)
}

fun convertiDataOraInTimestamp(data: String, ora: String): Long {
    val dataOraStringa = "$data $ora"
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val dataOra = formatter.parse(dataOraStringa)
    return dataOra?.time ?: 0
}