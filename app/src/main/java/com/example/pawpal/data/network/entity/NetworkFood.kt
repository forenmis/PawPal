package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.Food
import com.google.gson.annotations.SerializedName

data class NetworkFood(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("image")
    val image: String
)

fun NetworkFood.toFood(): Food {
    return Food(
        objectId = objectId,
        name = name,
        price = price,
        image = image
    )
}

fun Food.toNetworkFood(): Food {
    return Food(
        objectId = objectId,
        name = name,
        price = price,
        image = image
    )
}