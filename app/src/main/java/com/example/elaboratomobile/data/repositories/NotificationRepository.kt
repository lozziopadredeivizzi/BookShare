package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.LibroPrestitoDAO
import com.example.elaboratomobile.ui.screens.notification.Notification
import kotlinx.coroutines.flow.Flow
import java.util.Date

class NotificationRepository(private val libroPrestitoDAO: LibroPrestitoDAO) {

    suspend fun getCountNotification(username: String, date: Date): Int =
        libroPrestitoDAO.getCountLibriPrestitiNonVisualizzati(username, date)

    fun getNotifics(username: String, date: Date): Flow<List<Notification>> =
        libroPrestitoDAO.getNotificationsDetails(username, date)

    suspend fun updateVisualizzato(idPossesso: Int) =
        libroPrestitoDAO.updateVisualizzatoForIdPossesso(idPossesso)
}