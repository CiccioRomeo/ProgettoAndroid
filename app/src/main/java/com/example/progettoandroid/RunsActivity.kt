package com.example.progettoandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RunsActivity: AppCompatActivity() {
        private lateinit var settings : ImageView
        private lateinit var home : ImageView
        private lateinit var dbRun: MyDBHelperRun

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.statistiche_layout)


            settings = findViewById(R.id.settingsBtn_statistics)
            home = findViewById(R.id.homeBtn_statistics)

            val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val email: String? = sharedPreferences.getString("email", "")
            dbRun = MyDBHelperRun(this)

                val data : MutableList<String> = dbRun.getDate(email)
                val kmPercorsi : MutableList<String> = dbRun.getDistanze(email)
                val tempo : MutableList<String> = dbRun.getTempi(email)
                val calorieBruciate : MutableList<String> = dbRun.getCalorie(email)

                for (i in data.indices) {
                    val row = TableRow(this)
                    val params = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                    )
                    row.layoutParams = params

                    val dataTextView = TextView(this)
                    dataTextView.text = data[i]
                    dataTextView.gravity = Gravity.CENTER
                    dataTextView.textSize = 20F

                    val kmPercorsiTextView = TextView(this)
                    kmPercorsiTextView.text = kmPercorsi[i]
                    kmPercorsiTextView.gravity = Gravity.CENTER
                    kmPercorsiTextView.textSize = 20F

                    val tempoTextView = TextView(this)
                    tempoTextView.text = tempo[i]
                    tempoTextView.gravity = Gravity.CENTER
                    tempoTextView.textSize = 20F

                    val calorieBruciateTextView = TextView(this)
                    calorieBruciateTextView.text = calorieBruciate[i]
                    calorieBruciateTextView.gravity = Gravity.CENTER
                    calorieBruciateTextView.textSize = 20F

                    row.addView(dataTextView)
                    row.addView(kmPercorsiTextView)
                    row.addView(tempoTextView)
                    row.addView(calorieBruciateTextView)

                    tableLayout.addView(row)
                }


                settings.setOnClickListener {
                    val intent = Intent(this@RunsActivity, UserSettingsActivity::class.java)
                    startActivity(intent)
                }

                home.setOnClickListener {
                    val intent = Intent(this@RunsActivity, HomeActivity::class.java)
                    startActivity(intent)
                }

        }


        override fun onResume() {
            super.onResume()
        }

        override fun onRestart() {
            super.onRestart()
        }

        override fun onPause() {
            super.onPause()
        }

        override fun onStop() {
            super.onStop()
        }

        override fun onDestroy() {
            super.onDestroy()
        }


    private fun getArrayOf(i: Int, corse: MutableList<MutableList<String>>): MutableList<String> {
        val res: MutableList<String> = mutableListOf()
        for (corsa in corse) {
            res.add(corsa[i])
            Toast.makeText(this, corsa[i], Toast.LENGTH_SHORT).show()
        }
        return res
    }
}




