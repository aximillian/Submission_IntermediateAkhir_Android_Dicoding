package com.example.bstory.config.ext

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.bstory.config.AppConfig.MAXIMAL_SIZE
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun File.processFileImage(): File {
    val file = this
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}