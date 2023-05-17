package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.NetworkToy
import com.google.gson.annotations.SerializedName

data class GetToyResponse(
    @SerializedName("results")
    val results: List<NetworkToy>
)