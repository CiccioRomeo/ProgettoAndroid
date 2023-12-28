package com.example.progettoandroid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.location.Location
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions


class LocationManager(private val context: Context, private var timeInterval: Long, private var minimalDistance: Float, private val myMap : GoogleMap, private var KmBtn : TextView, private var KcalBtn :TextView, private val peso: Int) : LocationCallback() {

    private var request: LocationRequest
    var locationClient: FusedLocationProviderClient
    private lateinit var polyline : Polyline
    private var totalDistance: Double = 0.00;
    private  var  polylinelist : MutableList<Polyline> = arrayListOf()
    init {
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


    @SuppressLint("MissingPermission")
    fun startLocationTracking() {
        val polylineOptions = PolylineOptions()
        polylineOptions.color(Color.CYAN)
        polylineOptions.width(18f)
        polyline = myMap.addPolyline(polylineOptions)
        polylinelist.add(polyline)
        locationClient.requestLocationUpdates(request, this, Looper.getMainLooper())
        }

    @SuppressLint("MissingPermission")
    fun reStartLocationTracking() {
        locationClient.requestLocationUpdates(request, this, Looper.getMainLooper())
    }


    fun pauseLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
    }

    fun stopLocationTracking() {
        locationClient.flushLocations()
        locationClient.removeLocationUpdates(this)
        for(polyline: Polyline in polylinelist){
            polyline.remove()
        }
        polylinelist = arrayListOf()
        totalDistance = 0.00;
        KmBtn.text = "0.00 Km"
        KcalBtn.text = "0.0 Kcal"
    }

    override fun onLocationResult(location: LocationResult) {
        location ?: return
        val posizioni = location.locations
        val ultimaPosizione: Location = posizioni.last()
        val lat : Double = ultimaPosizione.latitude
        val lng : Double = ultimaPosizione.longitude
        updateTrack(LatLng(lat, lng))
    }

    override fun onLocationAvailability(availability: LocationAvailability) {
        // TODO: react on the availability change
    }

    private fun updateTrack(lastKnownLatLng: LatLng) {
        val points: MutableList<LatLng> = polyline.points
        points.add(lastKnownLatLng)
        polyline.points = points
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLatLng,16f))
        calculateKm()
        calculateCal()
    }

    private fun calculateCal() {
        val value : Double = 0.9
        val cal : Double = (peso*totalDistance*value)
        KcalBtn.text =  "%.1f".format(cal) + " Kcal"
    }

    fun calculateKm() {
            val points: MutableList<LatLng> = polylinelist[polylinelist.size - 1].points
            if(points.size >= 2 ){
                var currLocation: Location = Location("this")
                currLocation.latitude = points[points.size - 1].latitude
                currLocation.longitude = points[points.size - 1].longitude
                var lastLocation: Location = Location("this")

                lastLocation.latitude = points[points.size - 2].latitude
                lastLocation.longitude = points[points.size - 2].longitude
                val distance: Double = lastLocation.distanceTo(currLocation) / 1000.00
                totalDistance += distance
                KmBtn.text = "%.2f".format(totalDistance) + " Km"
            }
    }

}