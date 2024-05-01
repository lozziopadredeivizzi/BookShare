package com.example.elaboratomobile.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class UsernameRepository(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
    }

    val username = dataStore.data.map { it[USERNAME_KEY] ?: "" }

    suspend fun setCurrentUsername(value: String) = dataStore.edit { it[USERNAME_KEY] = value }
}