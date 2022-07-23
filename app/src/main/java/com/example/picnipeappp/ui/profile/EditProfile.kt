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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import java.io.ByteArrayOutputStream

class EditProfile : AppCompatActivity() {

    private var imgUri:Uri? = null
    private var bd = FirebaseFirestore.getInstance()

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
//        get_description_user.setText(UserSingleton.detail)

        topAppBarEditProfile.setNavigationOnClickListener {
            finish()
        }

        avEditPhotoUser.setOnClickListener {
            initAvatarView()
        }

        val btnEditUser = findViewById<Button>(R.id.btnEditUser)

        btnEditUser.setOnClickListener {
            val new_name_user = get_name_user.text.toString().trim()
            val new_description_user = get_description_user.text.toString().trim()
            val new_img_user = imgUri

            

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
}