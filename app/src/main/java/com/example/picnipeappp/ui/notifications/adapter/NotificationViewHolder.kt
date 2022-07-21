package com.example.picnipeappp.ui.notifications.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.notifications.Notification
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class NotificationViewHolder(view:View): RecyclerView.ViewHolder(view) {

    val avatarView = view.findViewById<AvatarView>(R.id.userAvatarView)
    val title = view.findViewById<TextView>(R.id.titleNotification)
    val message = view.findViewById<TextView>(R.id.messageNotification)
    fun render(notificationModel: Notification){

        title.text = notificationModel.title
        message.text = notificationModel.message
        avatarView.loadImage(notificationModel.photo)
    }
}