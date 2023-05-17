package com.example.pawpal.data.network.entity.response

import com.google.gson.annotations.SerializedName

data class UpdateAvatarRequest(
    @SerializedName("avatar")
    val avatar: String?
)
