package com.example.picnipeappp.data

import com.example.picnipeappp.data.model.User

class UserProvider {
    companion object {
        val usersList = listOf<User>(
            User(
                1,
                "Jordan",
                "Un chucha",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg"
            ),
            User(
                2,
                "Pedro",
                "Un huevon",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg"
            ),
            User(
                3,
                "Luis",
                "Uno mas",
                "https://pbs.twimg.com/media/EjKz0c0WsAQWJwK.jpg"
            ),
        )
    }
}