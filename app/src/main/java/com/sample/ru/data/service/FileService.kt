package com.sample.ru.data.service

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.sample.ru.util.toBitmap
import com.sample.ru.util.toJpeg
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FileService(private val context: Context) {

    fun saveBitmapFile(currentBitmap: Bitmap) {
        val mediaStorageDir = getMediaStorage()
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs()
        }
        val fileName = "JE_IMG_${System.currentTimeMillis()}".toJpeg()
        val file = File(mediaStorageDir, fileName)
        saveFile(file = file, bitmap = currentBitmap)
        fileName.removeSuffix(JPG)
    }

    private fun saveFile(file: File, bitmap: Bitmap) {
        with(FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
            flush()
            close()
        }
    }

    fun loadSavedImages(): List<Pair<File, Bitmap>> {
        val savedImages = ArrayList<Pair<File, Bitmap>>()
        val mediaStorageDir = getMediaStorage()
        mediaStorageDir.listFiles()?.let { data ->
            data.forEach { file ->
                val uri = file.toUri()
                val bitmap = uri.toBitmap(context)
                bitmap?.let {
                    savedImages.add(Pair(file, it))
                }
            }
            return savedImages
        } ?: return emptyList()
    }

    fun deleteFile(fileName: String): Boolean {
        val mediaStorageDir = getMediaStorage()
        val file = File(mediaStorageDir, fileName)
        return file.delete()
    }

    private fun getMediaStorage(): File {
        return File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Saved Images")
    }

    fun getFileLastModified(fileName: String): String {
        val mediaStorageDir = getMediaStorage()
        val file = File(mediaStorageDir, fileName)
        val lastModified = file.lastModified()
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(lastModified)
    }

    fun getFileUriByName(name: String): Uri {
        val mediaStorageDir = getMediaStorage()
        val file = File(mediaStorageDir, name)
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    companion object {
        private const val JPG = ".jpg"
    }
}