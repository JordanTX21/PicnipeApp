package com.example.picnipeappp.ui.register.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.picnipeappp.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val register = findViewById<Button>(R.id.register)
        val sig_in = findViewById<TextView>(R.id.tvSignInLink)

        register.setOnClickListener {

        }

        sig_in.setOnClickListener {
            finish()
        }
    }
}