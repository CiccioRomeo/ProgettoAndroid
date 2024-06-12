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
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val nome: EditText = findViewById(R.id.nome)
        val cognome: EditText = findViewById(R.id.cognome)
        val email: EditText = findViewById(R.id.email_register)
        val password: EditText = findViewById(R.id.password_register)
        val confermaPassword: EditText = findViewById(R.id.conferma_password)
        val continuaButton: Button = findViewById(R.id.continua_button)
        val peso: EditText = findViewById(R.id.peso)
        val db =  MyDBHelper(this);

        val loginRegister: TextView = findViewById(R.id.login_register)

        val text: String = "Sei gia registrato? Effettua il login"
        val ss: SpannableString = SpannableString(text);

        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        ss.setSpan(clickableSpan,32,37,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        loginRegister.text = ss;
        loginRegister.movementMethod = LinkMovementMethod.getInstance()

        continuaButton.setOnClickListener{
            val nomeT: String = nome.text.toString()
            val cognomeT: String = cognome.text.toString()
            val emailT: String = email.text.toString()
            val passwordT : String = password.text.toString()
            val pesoI : Int = peso.text.toString().toInt()
            val repasswordT: String = confermaPassword.text.toString()

            if(nomeT == "" || cognomeT == "" || emailT == "" || passwordT == "" || repasswordT == "")
                Toast.makeText(this@RegisterActivity, "Inserisci tutti i campi", Toast.LENGTH_SHORT).show();
            else{
                if(passwordT == repasswordT) {
                val checkEmail: Boolean? = db.checkEmail(emailT);
                if(checkEmail == false){
                    val insert: Boolean? = db.insertData(emailT, passwordT,nomeT,cognomeT,pesoI)
                    if(insert==true){
                        Toast.makeText(this@RegisterActivity, "Registrato con successo", Toast.LENGTH_SHORT).show();
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@RegisterActivity, "Registratione fallita", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Utente già registato! Effettua il Log In", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this@RegisterActivity, "La password non combacia", Toast.LENGTH_SHORT).show();
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

}
