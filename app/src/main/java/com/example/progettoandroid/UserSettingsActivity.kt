package com.example.progettoandroid

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserSettingsActivity : AppCompatActivity() {


    private lateinit var statistics : ImageView
    private lateinit var home: ImageView
    private lateinit var nome: TextView
    private lateinit var cognome: TextView
    private lateinit var peso: TextView
    private lateinit var salva: Button


    private lateinit var db : MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_layout)

        var intent : Intent = intent
        var email : String? = intent.getStringExtra("email")
        db = MyDBHelper(this)

        nome = findViewById(R.id.nome_settings)
        cognome = findViewById(R.id.cognome_settings)
        peso = findViewById(R.id.peso_settings)

        val nomeCorrente : String = db.getNome(email)
        val cognomeCorrente : String = db.getCognome(email)
        val pesoCorrente : Int = db.getPeso(email)

        setHint(nome,nomeCorrente)
        setHint( cognome, cognomeCorrente)
        setHint(peso, "$pesoCorrente")



        salva = findViewById(R.id.salva_settings)


         salva.setOnClickListener{
             val nuovoNome: String = nome.text.toString()
             val nuovoCognome: String = cognome.text.toString()
             val nuovoPeso: Int = peso.text.toString().toInt()
             Toast.makeText(this,"$nuovoNome, $nuovoCognome, $nuovoPeso",Toast.LENGTH_SHORT).show()
             salvaModifiche(email,nuovoNome,nuovoCognome,nuovoPeso)
         }


        statistics = findViewById(R.id.statisticBtn_settings)
        home = findViewById(R.id.homeBtn_settings)


        home.setOnClickListener{
            val intent = Intent(this@UserSettingsActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        statistics.setOnClickListener{
            val intent = Intent(this@UserSettingsActivity, StatisticsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setHint(textView: TextView, value :String) {
        textView.text = value
    }

    private fun salvaModifiche( email: String?, nuovoNome: String, nuovoCognome: String, nuovoPeso: Int) {
        if(nuovoNome != null){
            db.setNome(email, nuovoNome)
            setHint(nome,nuovoNome)
        }
        if(nuovoCognome != null){
            db.setCognome(email,nuovoCognome)
            setHint(cognome,nuovoCognome)
        }
        if(nuovoPeso != null){
            db.setPeso(email,nuovoPeso)
            setHint(peso,"$nuovoPeso")
        }
    }

}