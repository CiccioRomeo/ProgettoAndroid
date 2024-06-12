package com.example.progettoandroid

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import java.text.SimpleDateFormat
import java.util.Date


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
    private lateinit var dbRun : MyDBHelperRun
    var isPlay = false
    var pauseOffSet: Long = 0

    private lateinit  var currentLocation: Location
    private var currentLatitude: Double  = 0.0
    private var currentLongitude: Double = 0.0


    private  var email : String? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        email = sharedPreferences.getString("email","")


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


        dbRun = MyDBHelperRun(this)





        playBtn.setOnClickListener {
            if (!isPlay) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                chronometer.start()
                pauseBtn.visibility = View.VISIBLE
                playBtn.setImageResource(R.drawable.baseline_stop_circle_24)
                textMsg("La corsa è iniziata", this)
                isPlay = true
                locationManager.startLocationTracking()
                locationManager.locationClient.lastLocation
                    .addOnSuccessListener { location: Location ->
                        currentLocation = location // Salva la posizione corrente
                        currentLatitude = currentLocation.latitude
                        currentLongitude = currentLocation.longitude
                    }
            } else {
                val formatter = SimpleDateFormat("yy-MM-dd")
                val date = Date()
                val current = formatter.format(date)
                dbRun.insertData(email,current, Km.text.toString(),chronometer.text.toString(),cal.text.toString(),currentLatitude,currentLongitude)
                dbRun.close()
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
            if(isPlay) {
                val mAlertDialog = AlertDialog.Builder(this@HomeActivity)
                mAlertDialog.setTitle("Attenzione!") //set alertdialog title
                mAlertDialog.setMessage("Vuoi cancellare la tua corsa") //set alertdialog message
                mAlertDialog.setPositiveButton("Si") { dialog, id ->
                    val intent = Intent(this@HomeActivity, UserSettingsActivity::class.java)
                    startActivity(intent)
                }
                mAlertDialog.setNegativeButton("No") { dialog, id ->

                }
                mAlertDialog.show()
            }else{
                val intent = Intent(this@HomeActivity, UserSettingsActivity::class.java)
                startActivity(intent)
            }

        }
        statistics.setOnClickListener{
            if(isPlay) {
                val mAlertDialog = AlertDialog.Builder(this@HomeActivity)
                mAlertDialog.setTitle("Attenzione!") //set alertdialog title
                mAlertDialog.setMessage("Vuoi cancellare la tua corsa") //set alertdialog message
                mAlertDialog.setPositiveButton("Si") { dialog, id ->
                    val intent = Intent(this@HomeActivity, RunsActivity::class.java)
                    startActivity(intent)
                }
                mAlertDialog.setNegativeButton("No") { dialog, id ->

                }
                mAlertDialog.show()
            }else{
                val intent = Intent(this@HomeActivity, RunsActivity::class.java)
                startActivity(intent)
            }
         }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onRestart() {
        super.onRestart()
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
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

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        } else {
            // Richiedi la posizione corrente solo se hai i permessi
            locationManager.locationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        currentLocation = location
                        currentLatitude = currentLocation.latitude
                        currentLongitude = currentLocation.longitude
                        myMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(currentLatitude, currentLongitude),
                                15f
                            )
                        )
                        //textMsg("$currentLatitude-$currentLongitude", this)
                        dbRun.ultimaCorsaVicina(email,currentLatitude,currentLongitude,this@HomeActivity)
                    }
                }
        }
    }

    private fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
        }
        myMap.isMyLocationEnabled = true
    }
}












