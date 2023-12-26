package com.example.progettoandroid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class HomeActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationManager : LocationManager
    private lateinit var playBtn: ImageView
    private lateinit var pauseBtn: ImageView
    private lateinit var chronometer: Chronometer
    var isPlay = false
    var pauseOffSet: Long = 0
    private lateinit var lastLatLng : LatLng

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        playBtn = findViewById(R.id.playBtn)
        pauseBtn = findViewById(R.id.pauseBtn)
        chronometer = findViewById(R.id.chronoMterPlay)
        playBtn.setOnClickListener {
            if (!isPlay) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                chronometer.start()
                pauseBtn.visibility = View.VISIBLE
                playBtn.setImageResource(R.drawable.baseline_stop_circle_24)
                textMsg("La corsa è iniziata", this)
                isPlay = true
                locationManager.startLocationTracking()
            } else {
                chronometer.base = SystemClock.elapsedRealtime()
                pauseOffSet = 0
                chronometer.stop()
                playBtn.setImageResource(R.drawable.baseline_play_circle_24)
                pauseBtn.visibility = View.GONE
                textMsg("La corsa è finita", this)
                isPlay = false
                locationManager.stopLocationTracking();
            }
        }
        pauseBtn.setOnClickListener {
            if (isPlay) {
                chronometer.stop()
                pauseOffSet = SystemClock.elapsedRealtime() - chronometer.base
                isPlay = false
                pauseBtn.setImageResource(R.drawable.baseline_play_circle_24)
                textMsg("Pausa", this)
            } else {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                chronometer.start()
                pauseBtn.setImageResource(R.drawable.baseline_pause_circle_filled_24)
                textMsg("Hai ripreso la tua corsa", this)
                isPlay = true
            }
        }
    }

    fun textMsg(s: String, c: Context) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        myMap.uiSettings.isZoomControlsEnabled = true
        locationManager =  LocationManager(this,5000L,20.0F)
        setUpMap()
    }

    private fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        myMap.isMyLocationEnabled = true
        locationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLatLng = LatLng(location.latitude, location.longitude)
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 12f))

            }

        }

    }
}












