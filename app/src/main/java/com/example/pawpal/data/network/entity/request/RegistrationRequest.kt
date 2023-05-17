package com.example.pawpal.data.network.entity.request

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String
)