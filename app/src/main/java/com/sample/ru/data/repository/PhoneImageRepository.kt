package com.sample.ru.data.repository

import android.graphics.Bitmap
import com.sample.ru.data.service.FileService
import com.sample.ru.features.listPhoneImage.EmptyListPhoneImage
import com.sample.ru.features.listPhoneImage.ErrorListPhoneImage
import com.sample.ru.features.listPhoneImage.ListPhoneImageState
import com.sample.ru.features.listPhoneImage.SuccessListPhoneImage
import com.sample.ru.features.phoneImage.SavePhoneImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File

class PhoneImageRepository(
    private val fileService: FileService,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun savePhoneImage(currentBitmap: Bitmap): SavePhoneImage {
        return try {
            withContext(ioDispatcher) {
                fileService.saveBitmapFile(currentBitmap)
            }
            SavePhoneImage(true)
        } catch (throwable: Throwable) {
            SavePhoneImage(false)
        }
    }

    suspend fun getSavedImages(): ListPhoneImageState {
        return try {
            val files: List<Pair<File, Bitmap>> = withContext(ioDispatcher) {
                fileService.loadSavedImages()
            }
            if (files.isEmpty()) {
                EmptyListPhoneImage
            } else {
                SuccessListPhoneImage(files)
            }
        } catch (throwable: Throwable) {
            ErrorListPhoneImage
        }
    }

    suspend fun deleteImage(fileName: String): ListPhoneImageState {
        return try {
            val isDeleteFile: Boolean = withContext(ioDispatcher) {
                fileService.deleteFile(fileName)
            }
            if (isDeleteFile) {
                getSavedImages()
            } else {
                ErrorListPhoneImage
            }
        } catch (throwable: Throwable) {
            ErrorListPhoneImage
        }
    }

}