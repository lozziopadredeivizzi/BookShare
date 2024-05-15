package com.example.elaboratomobile.ui.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.elaboratomobile.ui.screens.map.BibliotecheLocation
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Map(
    lat: Double,
    long: Double,
    libraries: List<BibliotecheLocation>
) {
    val location = LatLng(lat, long)
    val locationState = MarkerState(position = location)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 16f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = locationState,
            title = "La tua posizione",

            )

        libraries.forEach { bibliotecaLocation ->
            val position =
                LatLng(bibliotecaLocation.latitudine ?: 0.0, bibliotecaLocation.longitudine ?: 0.0)
            Marker(
                state = MarkerState(position = position),
                title = bibliotecaLocation.nome,

            )
        }
    }
}

@Composable
fun EmptyMap() {
    val location = LatLng(41.9028, 12.4964) // Coordinate di Roma
    val locationState = MarkerState(position = location)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 5f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        cameraPositionState = cameraPositionState
    )
}