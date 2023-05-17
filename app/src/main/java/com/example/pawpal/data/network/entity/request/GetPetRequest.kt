package com.example.pawpal.data.network.entity.request

import com.google.gson.annotations.SerializedName

data class GetPetRequest(
    @SerializedName("userId")
    val userId: String
)