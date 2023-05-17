package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.NetworkFood
import com.google.gson.annotations.SerializedName

data class GetFoodResponse(
    @SerializedName("results")
    val results: List<NetworkFood>
)