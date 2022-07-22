package com.example.picnipeappp.ui.components

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.picnipeappp.R

class SelectImageDialogFragment(val mycallback:(Int) ->Unit) : DialogFragment()  {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val options = arrayOf<String>("Galería","Cámara")

            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.pick_image)
                .setItems(options,
                    DialogInterface.OnClickListener { dialog, which ->
                        mycallback(which)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}