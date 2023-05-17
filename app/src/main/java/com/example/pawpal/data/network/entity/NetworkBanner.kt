package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.Banner
import com.google.gson.annotations.SerializedName

data class NetworkBanner(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("link")
    val link: String,
    @SerializedName("image")
    val image: String
)

fun NetworkBanner.toBanner(): Banner {
    return Banner(
        objectId = objectId,
        title = title,
        description = description,
        discount = discount,
        link = link,
        image = image
    )
}
