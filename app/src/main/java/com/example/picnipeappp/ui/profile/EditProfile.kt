package com.example.picnipeappp.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.components.AddPostDialogFragment
import com.example.picnipeappp.ui.components.SelectImageDialogFragment
import com.example.picnipeappp.ui.login.UserSingleton
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayOutputStream

class EditProfile : AppCompatActivity() {

    private var imgUri:Uri? = null
    lateinit var storageReference: StorageReference
    lateinit var mauth : FirebaseAuth
    lateinit var authorUid : String
    private var firestore = FirebaseFirestore.getInstance()
    var dowloadImgUid : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        val topAppBarEditProfile = findViewById<MaterialToolbar>(R.id.topAppBarEditProfile)
        topAppBarEditProfile.title = "Editar Perfil"
        val avEditPhotoUser = findViewById<AvatarView>(R.id.avEditPhotoUser)
        avEditPhotoUser.loadImage(UserSingleton.photoPerfil)
        val get_name_user = findViewById<TextInputEditText>(R.id.tiEditUserName)
        val get_description_user = findViewById<TextInputEditText>(R.id.tiEditDescription)

        get_name_user.setText(UserSingleton.name)
        get_description_user.setText(UserSingleton.descripcion)

        topAppBarEditProfile.setNavigationOnClickListener {
            finish()
        }

        avEditPhotoUser.setOnClickListener {
            initAvatarView()
        }

        val btnEditUser = findViewById<Button>(R.id.btnEditUser)

        storageReference = FirebaseStorage.getInstance().getReference()
        mauth = FirebaseAuth.getInstance()
        authorUid = mauth.uid.toString()

        btnEditUser.setOnClickListener {
            val new_name_user = get_name_user.text.toString().trim()
            val new_description_user = get_description_user.text.toString().trim()
            val new_img_user = imgUri

            if(new_img_user!=null){
                val reference: StorageReference = storageReference.child("improfile").child(authorUid).child(new_img_user.toString())
                reference.putFile(new_img_user).addOnSuccessListener {
                    ObtainUrlImg(reference)
                }.addOnFailureListener {
                    Toast.makeText(this, "Error al subir archivo", Toast.LENGTH_SHORT).show()
                }
            }

            firestore.collection("users").document(UserSingleton.iduser.toString())
                .update(mapOf(
                    "Nombre" to new_name_user,
                    "descripcion" to new_description_user
                ))

            Toast.makeText(this, "Datos de usuario Actualizados", Toast.LENGTH_SHORT).show()
            UserSingleton.name = new_name_user
            UserSingleton.descripcion = new_description_user
        }
    }

    fun initAvatarView() {
        val dialog = SelectImageDialogFragment() { data -> onOptionSelected(data) }
        dialog.show(supportFragmentManager, "selectImageEditImage")
    }

    fun onOptionSelected(option: Int) {
        if (option == 0) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startForResult.launch(intent)
        } else if (option == 1) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
            openCamera.launch(intent)
        }
    }

    val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data!!
                val bitmap = data.extras!!.get("data") as Bitmap
                imgUri = getImageUriFromBitmap(this,bitmap)

                // Handle the Intent
                avEditPhotoUser.loadImage(bitmap)
            }
        }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data
                // Handle the Intent
                imgUri = data
//                val avEditPhotoUser = findViewById<AvatarView>(R.id.avEditPhotoUser)
                if(data==null){
                    avEditPhotoUser.loadImage("https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg")
                }else{
                    avEditPhotoUser.loadImage(data)
                }
            }
        }
    private fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri{
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    fun ObtainUrlImg(imagRef : StorageReference){
        imagRef.downloadUrl.addOnSuccessListener ( OnSuccessListener<Uri> { uri ->
            dowloadImgUid = uri.toString()
            firestore.collection("users").document(UserSingleton.iduser.toString())
                .update("fotoPerfil", dowloadImgUid).addOnSuccessListener {
                    UserSingleton.photoPerfil = dowloadImgUid
                }

        }
        )
    }
}