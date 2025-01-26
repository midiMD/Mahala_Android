package com.neighborly.neighborlyandroid.common.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun uriToFile(context: Context, uri: Uri): File {

    val mimeType = context.contentResolver.getType(uri) // data type
    // want to conserve the extension
    val fileName = when (mimeType) {
        "image/jpeg" -> "image.jpg"
        "image/png" -> "image.png"
        "image/gif" -> "image.gif"
        "image/webp" -> "image.webp"
        "image/bmp" -> "image.bmp"
        else -> "image.jpg"
    }
    Log.i("logs","Image uri MIME type: $mimeType")
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.cacheDir,fileName )
    file.outputStream().use { output ->
        inputStream?.copyTo(output)
    }
    return file
}

fun prepareImagePart(file: File, partName: String): MultipartBody.Part {
   // val requestFile = file.asRequestBody("image/${file.name.split(".")[1]}".toMediaTypeOrNull())
    val requestFile = file.asRequestBody(file.name.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}

fun prepareTextPart(value: String): RequestBody {
    return value.toRequestBody("text/plain".toMediaTypeOrNull())
}