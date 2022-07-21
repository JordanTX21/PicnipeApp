package com.example.picnipeappp.ui.register.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.picnipeappp.MainActivity
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityRegisterBinding
import com.example.picnipeappp.ui.login.usernameGlobal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterActivity : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val register = findViewById<Button>(R.id.register)
        val sig_in = findViewById<TextView>(R.id.tvSignInLink)


        register.setOnClickListener {


            val email = binding.emailR
            val name = binding.usernameR
            val password = binding.passwordR
            val repetirPassword = binding.repeatPasswordR
            val prueba = binding.idpruebaR
`
            val pruebatext: String = prueba.text.toString()

            Toast.makeText(this, pruebatext,Toast.LENGTH_SHORT).show()
            if (name.text.isNotEmpty() && email.text.isNotEmpty() && password.text.isNotEmpty() && repetirPassword.text.isNotEmpty()){

                if(password.text.toString() == repetirPassword.text.toString() ){

                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        this,
                                        "Usuario creado con exito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val mainIntent = Intent(this, MainActivity::class.java)
                                    var nombreUsuario = usernameGlobal()
                                    bd.collection("users").document(email.text.toString()).set(
                                        hashMapOf(
                                            "Nombre" to name.text.toString(),
                                            "fotoPerfil" to "default.png"
                                        )
                                    )
                                    startActivity(mainIntent)
                                }
                                else {
                                    Toast.makeText(
                                        this,
                                        "Error al crear usuario",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }

                }

                else{
                    Toast.makeText(this, "No son iguales los valores", Toast.LENGTH_SHORT).show()
                }
            }

            else{
                Toast.makeText(this, "Error al enviar datos: Algo vacio:" + name.text.toString() + " - "+ email.text.toString()+ " - "+ password.text.toString()+ " - "+ repetirPassword.text.toString()+ "l", Toast.LENGTH_SHORT).show()

            }
        }

        sig_in.setOnClickListener {
            finish()
        }
    }
}