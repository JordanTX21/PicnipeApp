package com.example.picnipeappp.ui.components

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.picnipeappp.R
import com.google.firebase.storage.StorageReference

class SelectImageDialogFragment(private val mycallback:(Uri?) ->Unit) : DialogFragment()  {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val options = arrayOf<String>("Galería","Cámara")
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.pick_image)
                .setItems(options,
                    DialogInterface.OnClickListener { dialog, which ->
                        if(which == 0){
                            //galeria
//                            Toast.makeText(context, "galeria", Toast.LENGTH_SHORT).show()
                            val intent = Intent(Intent.ACTION_GET_CONTENT)
                            intent.type = "image/*"
                            startForActivityResult.launch(intent)
                        }else if(which == 1){
                            //camara
//                            Toast.makeText(context, "camara", Toast.LENGTH_SHORT).show()
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE)
//                            startActivityForResult(intent, 1)
                            startForActivityResult.launch(intent)
                        }
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    private val startForActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data?.data
            //AQUI SE SETEA LA IMAGEN
            mycallback(data)
//            binding.imageView.setImageURI(data)
        }
    }
}