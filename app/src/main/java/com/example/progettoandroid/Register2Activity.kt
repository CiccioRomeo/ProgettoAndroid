package com.example.progettoandroid

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Register2Activity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register2)
    // Inizializza lo Spinner
        val genderSpinner: Spinner = findViewById(R.id.gender_spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // Imposta l'ascoltatore di selezione dello Spinner
        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Puoi eseguire azioni quando un elemento dello Spinner viene selezionato
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Chiamato quando nessun elemento è selezionato
            }
        }
    }

    // Metodo chiamato quando il pulsante "Mostra Genere" viene cliccato
    fun mostraGenere(view: View) {
        val genderSpinner: Spinner = findViewById(R.id.gender_spinner)
        val selectedGender: String = genderSpinner.selectedItem.toString()

        // Puoi fare qualcosa con il genere selezionato, ad esempio, visualizzarlo in un Toast
        Toast.makeText(this, "Genere selezionato: $selectedGender", Toast.LENGTH_SHORT).show()
    }
}