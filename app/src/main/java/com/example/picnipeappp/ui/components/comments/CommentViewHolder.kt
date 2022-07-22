package com.example.picnipeappp.ui.components.comments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.notifications.Notification
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.loadImage

class CommentViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val avatarView = view.findViewById<AvatarView>(R.id.userAvatarViewComment)
    val userNameComment = view.findViewById<TextView>(R.id.userNameComment)
    val message = view.findViewById<TextView>(R.id.messageComment)
    fun render(commentModel: Comment){
        userNameComment.text = commentModel.fromUserNameid
        message.text = commentModel.message
        avatarView.loadImage(commentModel.fromUserPhoto)
    }
}