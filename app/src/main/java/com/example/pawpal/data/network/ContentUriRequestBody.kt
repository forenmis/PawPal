package com.example.pawpal.data.network

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

class ContentUriRequestBody(
    private val contentResolver: ContentResolver,
    private val contentUri: Uri
) : RequestBody() {

    override fun contentType(): MediaType? {
        return contentResolver.getType(contentUri)?.toMediaTypeOrNull()
    }

    @SuppressLint("Recycle")
    override fun writeTo(sink: BufferedSink) {
        val inputStream = contentResolver.openInputStream(contentUri)
            ?: throw IllegalStateException("Couldn't open content URI for reading: $contentUri")
        val inputStreamSource = inputStream.source()
        inputStreamSource.use { source ->
            sink.writeAll(source)
        }
    }
}