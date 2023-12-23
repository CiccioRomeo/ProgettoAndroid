package com.example.progettoandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val emailLogin: EditText = findViewById(R.id.email_login)
        val passwordLogin: EditText = findViewById(R.id.password_login)
        val loginButton: Button = findViewById(R.id.login_login_button)
        val DB: MyDBHelper = MyDBHelper(this)
        loginButton.setOnClickListener {
            val emailT: String = emailLogin.text.toString()
            val passwordT: String = emailLogin.text.toString()
            if (emailT.equals("") || passwordT.equals(""))
                Toast.makeText(this@LoginActivity, "Inserisci tutti i campi", Toast.LENGTH_SHORT)
                    .show();
            else {
                val checkEmailPass: Boolean? = DB.checkEmailPassword(emailT, passwordT);
                if (checkEmailPass == true) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Registrazione avvenuta con successo",
                        Toast.LENGTH_SHORT
                    ).show();
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Credenziali non valide", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}