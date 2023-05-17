package com.example.pawpal.data.network.entity

import com.example.pawpal.entity.Pet
import com.google.gson.annotations.SerializedName

data class NetworkPet(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("userId")
    val userId: String
)

fun NetworkPet.toPet(): Pet {
    return Pet(
        objectId = objectId,
        name = name,
        age = age,
        userId = userId
    )
}

fun Pet.toNetworkPet(): NetworkPet {
    return NetworkPet(
        objectId = objectId,
        name = name,
        age = age,
        userId = userId
    )
}