package com.example.pawpal.data.network.entity.response

import com.example.pawpal.data.network.entity.NetworkBanner
import com.google.gson.annotations.SerializedName

data class GetBannerResponse(
    @SerializedName("results")
    val results: List<NetworkBanner>
)