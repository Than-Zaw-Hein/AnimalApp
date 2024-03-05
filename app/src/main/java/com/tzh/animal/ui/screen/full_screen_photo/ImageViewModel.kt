package com.tzh.animal.ui.screen.full_screen_photo

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.tzh.animal.ulti.MyFileProvider
import com.tzh.animal.ulti.MyFileProvider.Companion.getDownloadFilePath
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    val isDownloading: MutableState<Boolean> = mutableStateOf(false)
    val message: MutableState<String> = mutableStateOf("")

    fun downloadImageFromUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message.value = "Start downloading...."
            isDownloading.value = true
            delay(1000)
            val loader = ImageLoader(application)
            val request = ImageRequest.Builder(application).data(url)
                .allowHardware(false) // Disable hardware bitmaps
                .build()

            val bitmap = when (val result = loader.execute(request)) {
                is SuccessResult -> {
                    val drawable = result.drawable
                    if (drawable is BitmapDrawable) {
                        drawable.bitmap
                    } else {
                        null // Handle unexpected drawable type
                    }
                }

                is ErrorResult -> {
                    // Handle error (e.g., log or show a placeholder image)
                    null
                }

                else -> null // Handle other result types if needed
            }

            if (bitmap != null) {
                // Save the image to a file (you can customize the file path)
                val file = getDownloadFilePath(application)
                file.let {
                    Log.e("FILE path", file.absolutePath.toString())
                    persistImage(file, bitmap)
                }
            }

            isDownloading.value = false
        }
    }

    private fun persistImage(file: File, bitmap: Bitmap) {
        val os: OutputStream
        try {
            os = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()
            incrementImageCounter()
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = Uri.fromFile(file)
            application.sendBroadcast(mediaScanIntent)
            message.value = "Download complete"
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
            message.value = "Fail to download"
        }

    }

    private fun incrementImageCounter() {
        val sharedPreferences =
            application.getSharedPreferences(MyFileProvider.PREF_NAME, Context.MODE_PRIVATE)
        val currentCounter = sharedPreferences.getInt(MyFileProvider.KEY_COUNTER, 1)
        val newCounter = currentCounter + 1
        // Save the updated counter
        sharedPreferences.edit().putInt(MyFileProvider.KEY_COUNTER, newCounter).apply()


    }


}