package com.example.progettoandroid

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

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
    }
}