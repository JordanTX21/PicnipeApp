package com.example.picnipeappp.ui.notifications

data class Notification(
    val title: String,
    val message: String,
    val fromUserPhoto: String,
    val fromUserId: String,
    val toUserId: String,
    val fromUserName: String,
    ) {}