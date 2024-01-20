package com.example.progettoandroid

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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


        val RegisterLogin: TextView = findViewById(R.id.register_login)
        val text: String = "Non sei registrato? Registrati"
        val ss: SpannableString = SpannableString(text);

        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

        ss.setSpan(clickableSpan,20,30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        RegisterLogin.text = ss;
        RegisterLogin.movementMethod = LinkMovementMethod.getInstance()




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
                    saveEmail(emailT)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Credenziali non valide", Toast.LENGTH_SHORT).show();
                }
            }
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
    private fun saveEmail(email: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("email")
        editor.putString("email", email)
        editor.apply()
    }

}