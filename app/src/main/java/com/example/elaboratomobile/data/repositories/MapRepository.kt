package com.example.elaboratomobile.data.repositories

import com.example.elaboratomobile.data.database.BibliotecaDAO
import com.example.elaboratomobile.ui.screens.map.BibliotecheLocation
import kotlinx.coroutines.flow.Flow

class MapRepository(private val bibliotecaDAO: BibliotecaDAO) {

    fun getLibraries(): Flow<List<BibliotecheLocation>> = bibliotecaDAO.getBibliotecaLocations()

}