package com.example.progettoandroid

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.health.connect.datatypes.ExerciseRoute.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.Task

class HomeActivity : AppCompatActivity(),OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private lateinit var  myMap : GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var lastLocation : android.location.Location
    private val pathPoints = mutableListOf<LatLng>()
    private var polyline: Polyline? = null


    private lateinit var playBtn :ImageView
    private lateinit var pauseBtn : ImageView
    private lateinit var chronomter: Chronometer
    var isPlay = false
    var pauseOffSet :Long = 0

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val mapFragmet: SupportMapFragment = getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        mapFragmet.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



            /**set find id*/
            playBtn = findViewById(R.id.playBtn)
            pauseBtn = findViewById(R.id.pauseBtn)
            chronomter = findViewById(R.id.chronoMterPlay)
            playBtn.setOnClickListener {
                /**set play*/
                if (!isPlay){
                    chronomter.base = SystemClock.elapsedRealtime() - pauseOffSet
                    chronomter.start()
                    pauseBtn.visibility = View.VISIBLE
                    playBtn.setImageResource(R.drawable.baseline_stop_circle_24)
                    textMsg("La corsa è iniziata",this)
                    isPlay  =true

                }
                else{
                    chronomter.base = SystemClock.elapsedRealtime()
                    pauseOffSet = 0
                    chronomter.stop()
                    playBtn.setImageResource(R.drawable.baseline_play_circle_24)
                    pauseBtn.visibility = View.GONE
                    textMsg("La corsa è finita",this)
                    isPlay = false
                }

            }
            pauseBtn.setOnClickListener {
                if (isPlay){
                    chronomter.stop()
                    pauseOffSet = SystemClock.elapsedRealtime() - chronomter.base
                    isPlay = false
                    pauseBtn.setImageResource(R.drawable.baseline_play_circle_24)
                    textMsg("Pausa",this)
                }
                else{
                    chronomter.base = SystemClock.elapsedRealtime() - pauseOffSet
                    chronomter.start()
                    pauseBtn.setImageResource(R.drawable.baseline_pause_circle_filled_24)
                    textMsg("Hai ripreso la tua corsa",this)
                    isPlay = true
                }
            }

        }
        fun textMsg(s:String,c: Context){
            Toast.makeText(c,s,Toast.LENGTH_SHORT).show()
        }



    private fun drawPath() {
        polyline?.remove() // Rimuovi la vecchia Polyline se presente

        // Disegna la nuova Polyline con i punti del percorso
        if (pathPoints.size >= 2) {
            val polylineOptions = PolylineOptions()
                .addAll(pathPoints)
                .color(Color.BLUE)
                .width(5f)

            polyline = myMap.addPolyline(polylineOptions)
        }
    }
    private fun updatePath() {
        // Aggiungi la posizione corrente alla lista di punti del percorso
        val currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
        pathPoints.add(currentLatLng)

        // Disegna il percorso aggiornato sulla mappa
        drawPath()
    }



    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap;

        myMap.uiSettings.isZoomControlsEnabled = true;
        myMap.setOnMarkerClickListener(this)
        setUpMap();
    }

    private fun setUpMap() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_REQUEST_CODE)
            return
        }
        myMap.isMyLocationEnabled = true;
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                if (isPlay) {
                    updatePath()
                }
                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,12f))

            }

        }
    }
    private fun placeMarkerOnMap(currentLatLong: LatLng){
        val markerOptions= MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        myMap.addMarker(markerOptions)

    }

    override fun onMarkerClick(p0: Marker) = false
}