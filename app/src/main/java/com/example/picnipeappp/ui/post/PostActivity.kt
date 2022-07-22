package com.example.picnipeappp.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.picnipeappp.R
import com.example.picnipeappp.databinding.ActivityPostBinding
import com.example.picnipeappp.ui.components.comments.Comment
import com.example.picnipeappp.ui.components.comments.CommentAdapter
import com.example.picnipeappp.ui.components.comments.CommentProvider
import com.example.picnipeappp.ui.home.Post
import com.example.picnipeappp.ui.home.PostProvider
import com.example.picnipeappp.ui.login.UserSingleton
import com.example.picnipeappp.ui.notifications.Notification
import com.example.picnipeappp.ui.notifications.NotificationProvider
import com.example.picnipeappp.ui.notifications.adapter.NotificationAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private var firestore = FirebaseFirestore.getInstance()
    private val obtenerComents = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        binding = ActivityPostBinding.inflate(layoutInflater)

        val post_id = intent.getStringExtra("post_id")
        val post_photo = intent.getStringExtra("post_photo")
        val post_title = intent.getStringExtra("post_title")
        val post_content = intent.getStringExtra("post_content")
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val favPost = findViewById<FloatingActionButton>(R.id.favPost)

        var is_fav = false

        favPost.setOnClickListener {
            if (is_fav) {
                favPost.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Toast.makeText(this, "Ya no es favorito", Toast.LENGTH_SHORT).show()
            } else {
                favPost.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(this, "Favorito!", Toast.LENGTH_SHORT).show()
            }
            is_fav = !is_fav
        }

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        userName.text = post_title
        userDescription.text = post_content
//
        Glide.with(this).load(post_photo).into(imgPost)
        Glide.with(this).load(post_photo).into(userAvatarView)


        firestore.collection("publications").document(post_id.toString()).collection("comentarios").get().addOnSuccessListener { coments ->
            var provider = CommentProvider.commentsList
            provider.clear()
            Toast.makeText(this, coments.toString(), Toast.LENGTH_SHORT).show()
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

            var data = hashMapOf(
                "comment" to newComment.message,
                "commentUserName" to newComment.fromUserName,
                "commentUserID" to newComment.fromUserID,
                "fromUserPhoto" to newComment.fromUserPhoto
            )

            obtenerComents.collection("publications").document(post_id.toString()).collection("comentarios").add(data).addOnCompleteListener() {
                if(it.isSuccessful){
                    Toast.makeText(this, "guardado exitoso" , Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "error al ingreesar en la bd" , Toast.LENGTH_SHORT).show()
                }
            }

        }

        initRecyclerView()

    }

    fun initRecyclerView() {
//        Toast.makeText(this, CommentProvider.commentsList.size, Toast.LENGTH_SHORT).show()
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerviewComments)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = CommentAdapter(CommentProvider.commentsList)

    }
}