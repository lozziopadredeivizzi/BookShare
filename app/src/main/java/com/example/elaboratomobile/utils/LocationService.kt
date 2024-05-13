package com.example.elaboratomobile.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

enum class MonitoringStatus {
    Monitoring,
    Paused,
    NotMonitoring
}

data class Coordinates(val latitude: Double, val longitude: Double)

class LocationService(private val ctx: Context) {
    var isLocationEnabled: Boolean? by mutableStateOf(null)
        private set
    var monitoringStatus by mutableStateOf(MonitoringStatus.NotMonitoring)
        private set
    var coordinates: Coordinates? by mutableStateOf(null)
        private set

    // Gestisce avvio e interruzzione del monitoraggio
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ctx)
    //Definisce i parametri del monitoraggio
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        }.build()
    //Definisce le azioni all'ottenimento delle nuove coordinate
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            with(p0.locations.last()) {
                coordinates = Coordinates(latitude, longitude)
            }
            endLocationRequest()
        }
    }
    // Apre le impostazioni della posizione del dispositivo
    fun openLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (intent.resolveActivity(ctx.packageManager) != null) {
            ctx.startActivity(intent)
        }
    }
    // Richiede la posizione attuale del dispositivo.
    // Prima verifica che la posizione sia attiva e che ci siano i permessi
    fun requestCurrentLocation() {
        // Check if location is enabled
        val locationManager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isLocationEnabled != true) return

        // Check if permission is granted
        val permissionGranted = ContextCompat.checkSelfPermission(
            ctx,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!permissionGranted) return

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        monitoringStatus = MonitoringStatus.Monitoring
    }

    fun endLocationRequest() {
        if (monitoringStatus == MonitoringStatus.NotMonitoring) return
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        monitoringStatus = MonitoringStatus.NotMonitoring
    }

    fun pauseLocationRequest() {
        if (monitoringStatus != MonitoringStatus.Monitoring) return
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        monitoringStatus = MonitoringStatus.Paused
    }

    fun resumeLocationRequest() {
        if (monitoringStatus != MonitoringStatus.Paused) return
        requestCurrentLocation()
    }
}
