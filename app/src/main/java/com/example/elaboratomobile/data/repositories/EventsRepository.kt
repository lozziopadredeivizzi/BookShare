package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.Biblioteca
import com.example.elaboratomobile.data.database.Evento
import com.example.elaboratomobile.data.database.EventoDAO
import com.example.elaboratomobile.ui.screens.events.EventState
import kotlinx.coroutines.flow.Flow

class EventsRepository(private val eventoDAO: EventoDAO) {

    val events: Flow<List<EventState>> = eventoDAO.getAll()

    suspend fun upsert(event: Evento) = eventoDAO.upsert(event)

    suspend fun delete(item: Evento) = eventoDAO.delete(item)
}