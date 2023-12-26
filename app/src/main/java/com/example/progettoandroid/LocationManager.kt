package com.example.progettoandroid

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


@SuppressLint("MissingPermission")
class LocationManager(context: Context, private var timeInterval: Long, private var minimalDistance: Float) : LocationCallback() {

    private var request: LocationRequest
    private var locationClient: FusedLocationProviderClient
    private var context1 : Context
    init {
        // getting the location client
        context1 = context
        locationClient = LocationServices.getFusedLocationProviderClient(context)
        request = createRequest()
    }

        private fun createRequest(): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, timeInterval).apply {
            setMinUpdateDistanceMeters(minimalDistance)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()

    fun changeRequest(timeInterval: Long, minimalDistance: Float) {
        this.timeInterval = timeInterval
        this.minimalDistance = minimalDistance
        createRequest()
        stopLocationTracking()
        startLocationTracking()
    }

    fun startLocationTracking() =
        locationClient.requestLocationUpdates(request, this, Looper.getMainLooper())


    fun stopLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
    }

    override fun onLocationResult(location: LocationResult) {
        location ?: return
        val posizioni = location.locations
        val ultimaPosizione: Location = posizioni.last()
        val lat : Double = ultimaPosizione.latitude
        val lng : Double = ultimaPosizione.longitude
        Toast.makeText(context1,"$lat,$lng",Toast.LENGTH_SHORT).show()

    }

    override fun onLocationAvailability(availability: LocationAvailability) {
        // TODO: react on the availability change
    }

}