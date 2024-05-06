package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.Utente
import com.example.elaboratomobile.data.database.UtenteDAO

class UtenteRepository (private val utenteDAO: UtenteDAO) {

    suspend fun checkLogin(username: String, password: String): Utente? {
        return utenteDAO.checkLogin(username, password)
    }

    suspend fun checkUsername(username: String): Utente?{
        return utenteDAO.checkUsername(username)
    }

    suspend fun addUser(user: Utente){
        return utenteDAO.upsert(user)
    }
}