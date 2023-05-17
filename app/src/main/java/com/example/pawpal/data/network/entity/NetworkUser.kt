package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.User
import com.google.gson.annotations.SerializedName

data class NetworkUser(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("avatar")
    val avatar: String?
)

fun NetworkUser.toUser(): User {
    return User(
        email = email,
        username = username,
        avatar = avatar
    )
}

fun User.toNetworkUser(): NetworkUser {
    return NetworkUser(
        email = email,
        username = username,
        avatar = avatar
    )
}