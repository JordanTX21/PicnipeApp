package com.example.picnipeappp.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picnipeappp.R
import com.example.picnipeappp.ui.notifications.Notification

class NotificationAdapter(private val notificationsList: List<Notification>) : RecyclerView.Adapter<NotificationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotificationViewHolder(layoutInflater.inflate(R.layout.item_notification, parent,false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = notificationsList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = notificationsList.size
}