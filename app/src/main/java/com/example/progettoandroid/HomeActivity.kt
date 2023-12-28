package com.example.progettoandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment


class HomeActivity : AppCompatActivity(),OnMapReadyCallback {


    private lateinit var myMap: GoogleMap
    private lateinit var locationManager: LocationManager

    private lateinit var playBtn: ImageView
    private lateinit var pauseBtn: ImageView
    private lateinit var chronometer: Chronometer
    private lateinit var settings : ImageView
    private lateinit var statistics : ImageView
    private lateinit var home : ImageView


    private lateinit var Km : TextView
    private lateinit var cal : TextView
    private var peso : Int = 0
    private lateinit var db : MyDBHelper
    var isPlay = false
    var pauseOffSet: Long = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        var intent : Intent = intent
        var email : String? = intent.getStringExtra("email")


        settings =  findViewById(R.id.settingsBtn)
        statistics = findViewById(R.id.statisticBtn)
        home = findViewById(R.id.homeBtn)



        db = MyDBHelper(this);
        peso = db.getPeso(email)
        Km = findViewById(R.id.contaKm)
        cal = findViewById(R.id.contaCal)


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
                locationManager.pauseLocationTracking();
            } else {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                chronometer.start()
                pauseBtn.setImageResource(R.drawable.baseline_pause_circle_filled_24)
                textMsg("Hai ripreso la tua corsa", this)
                isPlay = true
                locationManager.startLocationTracking();
            }
        }
        settings.setOnClickListener {
            val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
            //intent.putExtra("email", email);
            startActivity(intent)
        }



        /* statistics.setOnClickListener{
             val intent = Intent(this@HomeActivity, SettingsActivity::class.java)
             intent.putExtra("email", email);
             startActivity(intent)
         }

         */


    }

    fun textMsg(s: String, c: Context) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        myMap.uiSettings.isZoomControlsEnabled = true
        setUpMap()
        locationManager = LocationManager(this, 3000L, 5.0F, myMap,Km,cal,peso)
    }

    private fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
        }
        myMap.isMyLocationEnabled = true
    }
}












