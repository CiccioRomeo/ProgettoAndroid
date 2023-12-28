package com.example.progettoandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val db =  MyDBHelper(this);


        val emailLogin: EditText = findViewById(R.id.email_login)
        val passwordLogin: EditText = findViewById(R.id.password_login)
        val loginButton: Button = findViewById(R.id.login_login_button)





        loginButton.setOnClickListener {
            val emailT: String = emailLogin.text.toString()
            val passwordT: String = passwordLogin.text.toString()
            if (emailT == "" || passwordT == "")
                Toast.makeText(this@LoginActivity, "Inserisci tutti i campi", Toast.LENGTH_SHORT)
                    .show();
            else {
                val checkEmailPass: Boolean? = db.checkEmailPassword(emailT, passwordT);
                if (checkEmailPass == true) {
                    Toast.makeText(this@LoginActivity, "Accesso avvenuto con successo", Toast.LENGTH_SHORT).show();
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("email", emailT);
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Credenziali non valide", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}