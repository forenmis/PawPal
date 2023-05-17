package com.example.pawpal.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.pawpal.R

fun ImageView.setImageRoundCorners(str: Any) {
    Glide.with(this)
        .load(str)
        .apply(RequestOptions().transform(RoundedCorners(50)))
        .into(this)
}

fun ImageView.setImage(str: Any) {
    Glide.with(this)
        .load(str)
        .into(this)
}

fun ImageView.setImageAvatar(str: Any?) {
    Glide.with(this)
        .load(str)
        .placeholder(R.drawable.shape_avatar)
        .apply(RequestOptions().transform(CircleCrop()))
        .into(this)
}

val ViewGroup.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)
