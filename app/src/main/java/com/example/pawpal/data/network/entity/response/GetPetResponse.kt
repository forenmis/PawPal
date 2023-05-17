package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.NetworkPet
import com.google.gson.annotations.SerializedName

data class GetPetResponse(
    @SerializedName("results")
    val results: List<NetworkPet>
)