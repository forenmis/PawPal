package com.example.pawpal.data.network.entity.response

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("sessionToken")
    val sessionToken: String,
    @SerializedName("objectId")
    val userId: String
)