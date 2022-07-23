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
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.login.usernameGlobal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        register.setOnClickListener {
            if(usernameR.text.isNotEmpty() && emailR.text.isNotEmpty() && passwordR.text.isNotEmpty() && repeatPasswordR.text.isNotEmpty()){

                if(passwordR.text.toString() == repeatPasswordR.text.toString() ){

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailR.text.toString(),passwordR.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "Usuario creado con exito",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val mainIntent = Intent(this, MainActivity::class.java)
                                val uid = FirebaseAuth.getInstance().currentUser?.uid

                                var userCreate = hashMapOf(
                                    "Nombre" to usernameR.text.toString(),
                                    "fotoPerfil" to "https://www.kindpng.com/picc/m/24-248253_user-profile-default-image-png-clipart-png-download.png",
                                    "Correo" to emailR.text.toString()
                                )


                                bd.collection("users").document(uid.toString()).set(userCreate)


                                var bienvenido = hashMapOf(
                                    "contenido" to "Te damos las gracias por elegirnos c:",
                                    "fromUserName" to "Equipo Picnipe",
                                    "fromUserPhoto" to "https://img.freepik.com/vector-premium/texto-bienvenida-estilo-memphis_136321-654.jpg?w=2000",
                                    "fromUseriD" to "admin",
                                    "titulo" to "Bienvenido a Picnipe",
                                    "toUserId" to uid.toString(),
                                    "type" to "like",
                                )

                                bd.collection("notifications").add(bienvenido)

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


            }else{
                Toast.makeText(this, "Error al enviar datos: Algo vacio:" + usernameR.text+ " - "+ emailR.text+ " - "+ passwordR.text+ " - "+ repeatPasswordR.text, Toast.LENGTH_SHORT).show()

            }

        }

        tvSignInLink.setOnClickListener {
            finish()
        }
    }
}