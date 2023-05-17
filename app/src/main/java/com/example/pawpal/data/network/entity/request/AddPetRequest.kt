package com.example.pawpal.data.network.entity.request

import com.google.gson.annotations.SerializedName

data class AddPetRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("userId")
    val userId: String
)
