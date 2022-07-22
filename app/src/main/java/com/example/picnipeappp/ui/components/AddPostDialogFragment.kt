package com.example.picnipeappp.ui.components

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.picnipeappp.R
import kotlinx.android.synthetic.main.dialog_add_post.*

class AddPostDialogFragment(val img:Uri?,val mycallback:(Uri?,String,String) ->Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction

            val inflater = requireActivity().layoutInflater;
            val v = inflater.inflate(R.layout.dialog_add_post, null)
            val toUploadImage = v.findViewById<ImageView>(R.id.toUploadImage)
            toUploadImage.setImageURI(img)
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_add_post)
                .setView(v)
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        val etContent = v.findViewById<EditText>(R.id.etContent)
                        val etTitle = v.findViewById<EditText>(R.id.etTitle)
                        mycallback(img,etContent.text.toString(), etTitle.text.toString())
                        // SE EJECUTA AL DAR OK
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // SE EJECUTA AL DAR CANCELAR
                    })
            //AQUI VA EL CODIGO

            //FIN

            // Create the AlertDialog object and return it
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}