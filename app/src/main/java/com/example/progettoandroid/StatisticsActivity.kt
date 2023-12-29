package com.example.progettoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

class StatisticsActivity : AppCompatActivity() {

        private lateinit var settings : ImageView
        private lateinit var home : ImageView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.statistics)

            settings = findViewById(R.id.settingsBtn_statistics)
            home = findViewById(R.id.homeBtn_statistics)

            val email: String? = intent.getStringExtra("email")
            val myFragment = Statistiche()
            val args = bundleOf("email" to email)
            myFragment.arguments = args



            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerViewStatistiche, myFragment)
                .commit()

            settings.setOnClickListener{
                val intent = Intent(this@StatisticsActivity, UserSettingsActivity::class.java)
                intent.putExtra("email", email);
                startActivity(intent)
            }

            home.setOnClickListener{
                val intent = Intent(this@StatisticsActivity, HomeActivity::class.java)
                //intent.putExtra("email", email);
                startActivity(intent)
            }
        }
    }