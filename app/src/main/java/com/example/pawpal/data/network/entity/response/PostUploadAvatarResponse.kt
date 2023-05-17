package com.example.pawpal.data.network.entity.response

import com.google.gson.annotations.SerializedName

data class PostUploadAvatarResponse(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("url")
        val url: String
    )
}