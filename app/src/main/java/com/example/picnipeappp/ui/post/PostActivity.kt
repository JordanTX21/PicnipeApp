package com.example.picnipeappp.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityPostBinding
import com.example.picnipeappp.ui.components.comments.Comment
import com.example.picnipeappp.ui.components.comments.CommentAdapter
import com.example.picnipeappp.ui.components.comments.CommentProvider
import com.example.picnipeappp.ui.login.UserSingleton
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private var firestore = FirebaseFirestore.getInstance()
    private var eliminarLike= FirebaseFirestore.getInstance()
    private val obtenerComents = FirebaseFirestore.getInstance()
    private val crearNotificacion= FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        binding = ActivityPostBinding.inflate(layoutInflater)

        val post_id = intent.getStringExtra("post_id")
        val post_photo = intent.getStringExtra("post_photo")
        val post_title = intent.getStringExtra("post_title")
        val post_content = intent.getStringExtra("post_content")
        val post_id_creator = intent.getStringExtra("post_creator")
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val favPost = findViewById<FloatingActionButton>(R.id.favPost)
        var is_fav = false

        if(post_id_creator == UserSingleton.iduser){
            favPost.visibility = View.GONE
        }else{
            favPost.visibility = View.VISIBLE
        }

        firestore.collection("users").document(UserSingleton.iduser.toString()).collection("likes").whereEqualTo("idpublicacion" ,post_id)
            .get().addOnSuccessListener {likes ->
                    for (like in likes) {
                        if(like.get("idpublicacion") != null){
                            is_fav = true
                            favPost.setImageResource(R.drawable.ic_baseline_favorite_24)
                        }
                    }
            }


        favPost.setOnClickListener {

            if (is_fav) {

                firestore.collection("users").document(UserSingleton.iduser.toString()).collection("likes").whereEqualTo("idpublicacion" ,post_id)
                    .get().addOnSuccessListener()  { likes ->
                        for (like in likes) {
                            eliminarLike.collection("users/${UserSingleton.iduser}/likes").document(like.id).delete()
                        }
                        favPost.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        Toast.makeText(this, "Eliminado de favorito", Toast.LENGTH_SHORT).show()
                        is_fav = false
                }

            } else {

                val dataLikes = hashMapOf(
                    "descripcion" to post_content,
                    "fotoPublicacion" to post_photo,
                    "idCreador" to "1",
                    "idpublicacion" to post_id,
                    "titulo" to post_title
                )

                val TeDieronLike = hashMapOf(
                    "contenido" to UserSingleton.name.toString() +" le dio like a tu publicacion: " + post_title,
                    "fromUserName" to UserSingleton.name.toString(),
                    "fromUserPhoto" to UserSingleton.photoPerfil.toString(),
                    "fromUseriD" to UserSingleton.iduser.toString(),
                    "titulo" to "Tienes un nuevo like",
                    "toUserId" to post_id_creator,
                    "type" to "like",
                )

                firestore.collection("users").document(UserSingleton.iduser.toString()).collection("likes").add(dataLikes).addOnCompleteListener {
                    if(it.isSuccessful){
                        favPost.setImageResource(R.drawable.ic_baseline_favorite_24)
                        Toast.makeText(this, "Agregado a favorito!", Toast.LENGTH_SHORT).show()
                        is_fav=true
                        crearNotificacion.collection("notifications").add(TeDieronLike)
                    }else{
                        Toast.makeText(this, "error like" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        userName.text = post_title
        userDescription.text = post_content

        Glide.with(this).load(post_photo).into(imgPost)
        Glide.with(this).load(post_photo).into(userAvatarView)


        firestore.collection("publications").document(post_id.toString()).collection("comentarios").get().addOnSuccessListener { coments ->
            val provider = CommentProvider.commentsList
            provider.clear()
            for (coment in coments) {
                provider.add(
                    Comment(
                        coment.id,
                        coment.get("commentUserID").toString(),
                        coment.get("commentUserName").toString(),
                        coment.get("fromUserPhoto").toString(),
                        coment.get("comment").toString(),
                    )
                )
            }
            initRecyclerView()
        }

        val btnSendComment = findViewById<Button>(R.id.btnSendComment)

        btnSendComment.setOnClickListener{
//            Toast.makeText(this, "Comentando", Toast.LENGTH_SHORT).show()
            val comment = tiComment.text.toString().trim()
            if(comment == ""){
                Toast.makeText(this, "Ingrese un comentario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val newComment = Comment(
                "1",
                UserSingleton.iduser.toString(),
                UserSingleton.name.toString(),
                UserSingleton.photoPerfil.toString(),
                comment
            )
            tiComment.text?.clear()
            CommentProvider.commentsList.add(newComment)


            initRecyclerView()

            //AQUI VA TU CODIGO

            val data = hashMapOf(
                "comment" to newComment.message,
                "commentUserName" to newComment.fromUserName,
                "commentUserID" to newComment.fromUserID,
                "fromUserPhoto" to newComment.fromUserPhoto
            )


            val comentaronNotificacion = hashMapOf(
                "contenido" to UserSingleton.name.toString() + " comento tu publicacion: " + newComment.message,
                "fromUserName" to UserSingleton.name.toString(),
                "fromUserPhoto" to UserSingleton.photoPerfil.toString(),
                "fromUseriD" to UserSingleton.iduser.toString(),
                "titulo" to "Tienes un nuevo comentario",
                "toUserId" to post_id_creator,
                "type" to "comentario",
            )

            obtenerComents.collection("publications").document(post_id.toString()).collection("comentarios").add(data).addOnCompleteListener() {
                if(it.isSuccessful){
                    Toast.makeText(this, "guardado exitoso" , Toast.LENGTH_SHORT).show()
                    crearNotificacion.collection("notifications").add(comentaronNotificacion)

                }else{
                    Toast.makeText(this, "error al ingreesar en la bd" , Toast.LENGTH_SHORT).show()
                }
            }

        }

        initRecyclerView()

    }

    fun initRecyclerView() {
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerviewComments)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = CommentAdapter(CommentProvider.commentsList)

    }




}