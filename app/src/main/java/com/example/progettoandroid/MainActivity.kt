package com.example.progettoandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginButton: Button = findViewById(R.id.log_in_button)
        val registerButton: Button = findViewById(R.id.register_button)

        loginButton.setOnClickListener{
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener{
            val intent1 = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent1)
        }
    }
}