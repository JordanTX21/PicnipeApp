package com.example.picnipeappp.ui.register.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.picnipeappp.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            Toast.makeText(this, username.text, Toast.LENGTH_SHORT).show()
        }

        tvSignInLink.setOnClickListener {
            finish()
        }
    }
}