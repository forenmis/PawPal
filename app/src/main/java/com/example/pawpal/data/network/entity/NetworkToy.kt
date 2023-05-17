package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.Toy
import com.google.gson.annotations.SerializedName

data class NetworkToy(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("name")
    val name: String,
)

fun NetworkToy.toToy(): Toy {
    return Toy(
        objectId = objectId,
        name = name,
        price = price,
        image = image
    )
}

fun Toy.toNetworkToy(): NetworkToy {
    return NetworkToy(
        objectId = objectId,
        name = name,
        price = price,
        image = image
    )
}